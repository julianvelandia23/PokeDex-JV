package com.julianvelandia.domain

interface PokeDexRepository {
    suspend fun getListPokemon(): Result<List<Pokemon>>

    suspend fun getPokemonDetail(name: String): Result<PokemonDetail>
}