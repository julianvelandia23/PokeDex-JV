package com.julianvelandia.domain

interface PokeDexRepository {
    suspend fun getListPokemon(): Result<List<Pokemon>>
}