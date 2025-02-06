package com.julianvelandia.data.remote

import javax.inject.Inject

class RemoteDataStorage @Inject constructor(
    private val pokeDexApi: PokeDexApi
) {
    suspend fun getListPokemon() = pokeDexApi.getPokemonLimit()
}