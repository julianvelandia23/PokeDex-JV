package com.julianvelandia.domain

import kotlinx.coroutines.flow.Flow

interface PokeDexRepository {
    fun getListPokemon(): Flow<Result<List<Pokemon>>>
    suspend fun searchPokemon(query: String): Result<List<Pokemon>>

    fun getPokemonDetail(name: String): Flow<Result<PokemonDetail?>>

}