package com.julianvelandia.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val VERSION_API = "v2/"

private const val GET_POKEMON_LIST_LIMIT = VERSION_API + "pokemon?limit=151"

private const val POKEMON_NAME = "name"
private const val GET_DETAIL_POKEMON = VERSION_API + "pokemon/{$POKEMON_NAME}"


interface PokeDexApi {

    @GET(GET_POKEMON_LIST_LIMIT)
    suspend fun getPokemonLimit(): Response<ResponseListDto<PokemonDto>>

    @GET(GET_DETAIL_POKEMON)
    suspend fun getDetailPokemon(@Path(POKEMON_NAME) name: String): Response<PokemonDetailDto>
}