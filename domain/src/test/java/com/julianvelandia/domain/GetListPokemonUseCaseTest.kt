package com.julianvelandia.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetListPokemonUseCaseTest {

    private lateinit var useCase: GetListPokemonUseCase
    private val repository: PokeDexRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = GetListPokemonUseCase(repository)
    }

    @Test
    fun `invoke returns list of pokemon when repository emits success`() = runTest {
        val expectedPokemonList = listOf(
            Pokemon(name = "Bulbasaur"),
            Pokemon(name = "Charmander")
        )

        val expectedResult = Result.success(expectedPokemonList)

        coEvery { repository.getListPokemon() } returns flowOf(expectedResult)

        val result = useCase().first()

        assertTrue(result.isSuccess)
        assertEquals(expectedPokemonList, result.getOrNull())

        coVerify { repository.getListPokemon() }
    }

    @Test
    fun `invoke returns error when repository emits failure`() = runTest {
        val exception = Exception("Network error")
        val expectedResult = Result.failure<List<Pokemon>>(exception)

        coEvery { repository.getListPokemon() } returns flowOf(expectedResult)

        val result = useCase().first()

        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke returns empty list when repository emits success with empty list`() = runTest {
        val expectedResult = Result.success(emptyList<Pokemon>())

        coEvery { repository.getListPokemon() } returns flowOf(expectedResult)

        val result = useCase().first()

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Pokemon>(), result.getOrNull())
    }
}
