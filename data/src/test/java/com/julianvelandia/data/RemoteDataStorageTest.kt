package com.julianvelandia.data

import com.julianvelandia.data.remote.AbilitiesDto
import com.julianvelandia.data.remote.AbilityDto
import com.julianvelandia.data.remote.FormsDto
import com.julianvelandia.data.remote.PokeDexApi
import com.julianvelandia.data.remote.PokemonDetailDto
import com.julianvelandia.data.remote.PokemonDto
import com.julianvelandia.data.remote.RemoteDataStorage
import com.julianvelandia.data.remote.ResponseListDto
import com.julianvelandia.data.remote.TypeDto
import com.julianvelandia.data.remote.TypesDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RemoteDataStorageTest {

    @MockK
    private lateinit var pokeDexApi: PokeDexApi

    private lateinit var remoteDataStorage: RemoteDataStorage

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteDataStorage = RemoteDataStorage(pokeDexApi)
    }

    @Test
    fun `getListPokemon calls pokeDexApi getPokemonLimit`() = runTest {
        val expectedList = listOf(
            PokemonDto(name = "Bulbasaur"),
            PokemonDto(name = "Ivysaur")
        )
        val responseListDto = ResponseListDto(results = expectedList)
        val expectedResponse = Response.success(responseListDto)

        coEvery { pokeDexApi.getPokemonLimit() } returns expectedResponse

        val result = remoteDataStorage.getListPokemon()

        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { pokeDexApi.getPokemonLimit() }
    }

    @Test
    fun `getListPokemon handles error response`() = runTest {
        val errorResponse: Response<ResponseListDto<PokemonDto>> = Response.error(
            500,
            "Server error".toResponseBody("application/json".toMediaTypeOrNull())
        )

        coEvery { pokeDexApi.getPokemonLimit() } returns errorResponse

        val result = remoteDataStorage.getListPokemon()

        assertEquals(errorResponse, result)
        coVerify(exactly = 1) { pokeDexApi.getPokemonLimit() }
    }

    @Test
    fun `getListPokemon handles empty response`() = runTest {
        val responseListDto = ResponseListDto(results = emptyList<PokemonDto>())
        val expectedResponse = Response.success(responseListDto)

        coEvery { pokeDexApi.getPokemonLimit() } returns expectedResponse

        val result = remoteDataStorage.getListPokemon()

        assertTrue(result.body()?.results?.isEmpty() == true)
        coVerify(exactly = 1) { pokeDexApi.getPokemonLimit() }
    }

    @Test
    fun `getPokemonDetail calls pokeDexApi getDetailPokemon`() = runTest {
        val pokemonName = "Eevee"
        val expectedDetail = PokemonDetailDto(
            id = 133,
            name = "Eevee",
            abilities = listOf(AbilitiesDto(AbilityDto("Run Away"))),
            forms = listOf(FormsDto("Eevee")),
            types = listOf(TypesDto(TypeDto("Normal")))
        )
        val expectedResponse = Response.success(expectedDetail)

        coEvery { pokeDexApi.getDetailPokemon(pokemonName) } returns expectedResponse

        val result = remoteDataStorage.getPokemonDetail(pokemonName)

        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { pokeDexApi.getDetailPokemon(pokemonName) }
    }

    @Test
    fun `getPokemonDetail handles error response`() = runTest {
        val pokemonName = "Eevee"
        val errorResponse: Response<PokemonDetailDto> = Response.error(
            404,
            "Not Found".toResponseBody("application/json".toMediaTypeOrNull())
        )

        coEvery { pokeDexApi.getDetailPokemon(pokemonName) } returns errorResponse

        val result = remoteDataStorage.getPokemonDetail(pokemonName)

        assertEquals(errorResponse, result)
        coVerify(exactly = 1) { pokeDexApi.getDetailPokemon(pokemonName) }
    }

    @Test
    fun `getPokemonDetail handles null response`() = runTest {
        val pokemonName = "Eevee"
        val expectedResponse: Response<PokemonDetailDto> = Response.success(null)

        coEvery { pokeDexApi.getDetailPokemon(pokemonName) } returns expectedResponse

        val result = remoteDataStorage.getPokemonDetail(pokemonName)

        assertNull(result.body())
        coVerify(exactly = 1) { pokeDexApi.getDetailPokemon(pokemonName) }
    }
}

