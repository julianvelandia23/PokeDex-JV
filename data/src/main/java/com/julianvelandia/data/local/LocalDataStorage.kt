package com.julianvelandia.data.local

import com.julianvelandia.data.local.detail.DetailPokemonDao
import com.julianvelandia.data.local.detail.DetailPokemonEntity
import com.julianvelandia.data.local.home.HomePokemonDao
import com.julianvelandia.data.local.home.HomePokemonEntity
import javax.inject.Inject

class LocalDataStorage @Inject constructor(
    private val homePokemonDao: HomePokemonDao,
    private val detailPokemonDao: DetailPokemonDao
) {

    suspend fun upsertHome(
       homePokemonEntity: HomePokemonEntity
    ) = homePokemonDao.upsert(homePokemonEntity)

    suspend fun getAll() = homePokemonDao.getAll()

    suspend fun searchPokemon(query: String) = homePokemonDao.searchPokemon(query)

    suspend fun upsertDetail(
        detailPokemonEntity: DetailPokemonEntity
    ) = detailPokemonDao.upsert(detailPokemonEntity)

    fun getPokemonDetail(name: String) = detailPokemonDao.getPokemon(name)

}