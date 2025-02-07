package com.julianvelandia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.julianvelandia.data.local.detail.DetailPokemonDao
import com.julianvelandia.data.local.detail.DetailPokemonEntity
import com.julianvelandia.data.local.home.HomePokemonDao
import com.julianvelandia.data.local.home.HomePokemonEntity

@Database(
    entities = [HomePokemonEntity::class, DetailPokemonEntity::class], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun homePokemonDao(): HomePokemonDao
    abstract fun detailPokemonDao(): DetailPokemonDao
}