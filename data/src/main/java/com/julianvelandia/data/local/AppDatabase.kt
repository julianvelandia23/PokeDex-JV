package com.julianvelandia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HomePokemonEntity::class], version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun homePokemonDao(): HomePokemonDao
}