package com.julianvelandia.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeDexApi {

    @GET("v2/pokemon?limit=151")
    suspend fun getPokemonLimit(): Response<ResponseListDto<PokemonDto>>
}