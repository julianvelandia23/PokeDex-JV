package com.julianvelandia.data.remote

import javax.inject.Inject

class RemoteDataStorage @Inject constructor(
    private val pokeDexApi: PokeDexApi
) {
    suspend fun getListPokemon() = pokeDexApi.getPokemonLimit()

    suspend fun getPokemonDetail(name: String) = pokeDexApi.getDetailPokemon(name)
}