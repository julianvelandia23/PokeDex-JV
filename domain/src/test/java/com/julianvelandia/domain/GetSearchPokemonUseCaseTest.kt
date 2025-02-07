package com.julianvelandia.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSearchPokemonUseCaseTest {

    private lateinit var useCase: GetSearchPokemonUseCase
    private val repository: PokeDexRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = GetSearchPokemonUseCase(repository)
    }

    @Test
    fun `invoke returns list of pokemon when repository search is successful`() = runTest {
        val query = "Pika"
        val expectedPokemonList = listOf(
            Pokemon(name = "Pikachu"),
            Pokemon(name = "Pichu")
        )

        val expectedResult = Result.success(expectedPokemonList)

        coEvery { repository.searchPokemon(query) } returns expectedResult

        val result = useCase(query)

        assertTrue(result.isSuccess)
        assertEquals(expectedPokemonList, result.getOrNull())

        coVerify { repository.searchPokemon(query) }
    }

    @Test
    fun `invoke returns error when repository search fails`() = runTest {
        val query = "Pika"
        val exception = Exception("Network error")
        val expectedResult = Result.failure<List<Pokemon>>(exception)

        coEvery { repository.searchPokemon(query) } returns expectedResult

        val result = useCase(query)

        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke returns empty list when no pokemon is found`() = runTest {
        val query = "Unknown"
        val expectedResult = Result.success(emptyList<Pokemon>())

        coEvery { repository.searchPokemon(query) } returns expectedResult

        val result = useCase(query)

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Pokemon>(), result.getOrNull())
    }
}
