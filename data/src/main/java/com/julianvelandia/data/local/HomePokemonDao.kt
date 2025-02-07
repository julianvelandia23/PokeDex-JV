package com.julianvelandia.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HomePokemonDao {
    @Upsert
    suspend fun upsert(entity: HomePokemonEntity)

    @Query("SELECT * FROM HomePokemonEntity")
    suspend fun getAll(): List<HomePokemonEntity>
}