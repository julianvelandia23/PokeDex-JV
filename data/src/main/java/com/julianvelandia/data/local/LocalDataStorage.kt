package com.julianvelandia.data.local

import javax.inject.Inject

class LocalDataStorage @Inject constructor(
    private val homePokemonDao: HomePokemonDao
) {

    suspend fun upsert(
       homePokemonEntity: HomePokemonEntity
    ) = homePokemonDao.upsert(homePokemonEntity)

    suspend fun getAll() = homePokemonDao.getAll()

    suspend fun searchPokemon(query: String) = homePokemonDao.searchPokemon(query)
}