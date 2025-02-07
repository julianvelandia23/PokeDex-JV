package com.julianvelandia.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDetailPokemonUseCaseTest {

    private lateinit var useCase: GetDetailPokemonUseCase
    private val repository: PokeDexRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetDetailPokemonUseCase(repository)
    }

    @Test
    fun `invoke calls repository and returns expected pokemon detail`() = runTest {
        val pokemonName = "Pikachu"
        val expectedPokemon = PokemonDetail(
            name = "Pikachu",
            image = "https://example.com/pikachu.png",
            abilities = listOf(Abilities(Ability("Static")), Abilities(Ability("Lightning Rod"))),
            forms = listOf(Forms("pikachu")),
            types = listOf(Types(Type("Electric")))
        )

        val expectedResult = Result.success(expectedPokemon)

        coEvery { repository.getPokemonDetail(pokemonName) } returns flowOf(expectedResult)

        val result = useCase(pokemonName).first()

        assertEquals(expectedResult, result)

        coVerify { repository.getPokemonDetail(pokemonName) }
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val pokemonName = "Pikachu"
        val exception = Exception("Network error")
        val expectedResult = Result.failure<PokemonDetail?>(exception)

        coEvery { repository.getPokemonDetail(pokemonName) } returns flowOf(expectedResult)

        val result = useCase(pokemonName).first()

        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke returns null when pokemon is not found`() = runTest {
        val pokemonName = "MissingNo"
        val expectedResult = Result.success<PokemonDetail?>(null)

        coEvery { repository.getPokemonDetail(pokemonName) } returns flowOf(expectedResult)

        val result = useCase(pokemonName).first()

        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }
}
