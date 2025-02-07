package com.julianvelandia.data.local.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomePokemonEntity(
    @PrimaryKey val name: String
)
