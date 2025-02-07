package com.julianvelandia.data

import com.julianvelandia.data.local.LocalDataStorage
import com.julianvelandia.data.local.home.HomePokemonEntity
import com.julianvelandia.data.local.home.toDomain
import com.julianvelandia.data.remote.RemoteDataStorage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class PokeDexRepositoryImplTest {

    @MockK
    private lateinit var remoteDataStorage: RemoteDataStorage

    @MockK
    private lateinit var localDataStorage: LocalDataStorage

    private lateinit var repository: PokeDexRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PokeDexRepositoryImpl(remoteDataStorage, localDataStorage)
    }

    @Test
    fun `getListPokemon returns local data when available`() = runTest {
        val localData = listOf(
            HomePokemonEntity(name = "Pikachu"),
            HomePokemonEntity(name = "Charmander")
        )
        coEvery { localDataStorage.getAll() } returns localData

        val flow = repository.getListPokemon().first()

        assertTrue(flow.isSuccess)
        assertEquals(localData.map { it.toDomain() }, flow.getOrNull())
    }

    @Test
    fun `getListPokemon handles remote call failure`() = runTest {
        coEvery { localDataStorage.getAll() } returns emptyList()
        coEvery { remoteDataStorage.getListPokemon() } throws IOException("Network error")

        val flow = repository.getListPokemon().first()

        assertTrue(flow.isFailure)
        assertTrue(flow.exceptionOrNull() is IOException)
    }

    @Test
    fun `searchPokemon returns results from local storage`() = runTest {
        val query = "Pika"
        val localResults = listOf(HomePokemonEntity(name = "Pikachu"))
        coEvery { localDataStorage.searchPokemon(query) } returns localResults

        val result = repository.searchPokemon(query)

        assertTrue(result.isSuccess)
        assertEquals(localResults.map { it.toDomain() }, result.getOrNull())
    }

    @Test
    fun `searchPokemon returns empty list when an error occurs`() = runTest {
        val query = "Pika"
        coEvery { localDataStorage.searchPokemon(query) } throws IOException("Database error")

        val result = repository.searchPokemon(query)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `getPokemonDetail handles remote call failure`() = runTest {
        val pokemonName = "Eevee"
        every { localDataStorage.getPokemonDetail(pokemonName) } returns flowOf(null)

        coEvery { remoteDataStorage.getPokemonDetail(pokemonName) } throws IOException("Network error")

        val flow = repository.getPokemonDetail(pokemonName).first()

        assertTrue(flow.isFailure)
        assertTrue(flow.exceptionOrNull() is IOException)
    }
}
