package com.julianvelandia.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface HomePokemonDao {
    @Upsert
    suspend fun upsert(entity: HomePokemonEntity)

    @Query("SELECT * FROM HomePokemonEntity")
    suspend fun getAll(): List<HomePokemonEntity>

    @Query("SELECT * FROM HomePokemonEntity WHERE name LIKE '%' || :query || '%'")
    suspend fun searchPokemon(query: String): List<HomePokemonEntity>
}