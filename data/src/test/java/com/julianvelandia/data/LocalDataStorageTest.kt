package com.julianvelandia.data

import com.julianvelandia.data.local.LocalDataStorage
import com.julianvelandia.data.local.detail.DetailPokemonDao
import com.julianvelandia.data.local.home.HomePokemonDao
import com.julianvelandia.data.local.home.HomePokemonEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalDataStorageTest {

    private lateinit var localDataStorage: LocalDataStorage
    private val homePokemonDao: HomePokemonDao = mockk(relaxed = true)
    private val detailPokemonDao: DetailPokemonDao = mockk(relaxed = true)

    @Before
    fun setUp() {
        localDataStorage = LocalDataStorage(homePokemonDao, detailPokemonDao)
    }

    @Test
    fun `upsertHome calls homePokemonDao upsert`() = runTest {
        val entity = HomePokemonEntity(id = 1, name = "Bulbasaur")

        coEvery { homePokemonDao.upsert(entity) } just Runs

        localDataStorage.upsertHome(entity)

        coVerify(exactly = 1) { homePokemonDao.upsert(entity) }
    }

    @Test
    fun `getAll returns data from homePokemonDao`() = runTest {
        val pokemonList = listOf(HomePokemonEntity(id = 1, name = "Squirtle"))

        coEvery { homePokemonDao.getAll() } returns pokemonList

        val result = localDataStorage.getAll()

        assertEquals(pokemonList, result)
    }

    @Test
    fun `searchPokemon returns filtered list from homePokemonDao`() = runTest {
        val query = "Pikachu"
        val filteredList = listOf(HomePokemonEntity(id = 25, name = "Pikachu"))

        coEvery { homePokemonDao.searchPokemon(query) } returns filteredList

        val result = localDataStorage.searchPokemon(query)

        assertEquals(filteredList, result)
    }

    @Test
    fun `upsertDetail calls detailPokemonDao upsert`() = runTest {
        val entity = dummyDetailPokemon

        coEvery { detailPokemonDao.upsert(entity) } just Runs

        localDataStorage.upsertDetail(entity)

        coVerify(exactly = 1) { detailPokemonDao.upsert(entity) }
    }

    @Test
    fun `getPokemonDetail returns data from detailPokemonDao`() = runTest {
        val pokemonName = "Eevee"

        every { detailPokemonDao.getPokemon(pokemonName) } returns flowOf(dummyDetailPokemon)

        val result = localDataStorage.getPokemonDetail(pokemonName).firstOrNull()

        assertEquals(dummyDetailPokemon, result)
    }

}


