package com.julianvelandia.data.local.detail

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailPokemonDao {

    @Upsert
    suspend fun upsert(pokemon: DetailPokemonEntity)

    @Query("SELECT * FROM DetailPokemonEntity WHERE name = :name")
    fun getPokemon(name: String): Flow<DetailPokemonEntity?>
}