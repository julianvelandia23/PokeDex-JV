package com.julianvelandia.data.local.detail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailPokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "abilities") val abilities: List<AbilityEntity>,
    @ColumnInfo(name = "forms") val forms: List<FormEntity>,
    @ColumnInfo(name = "types") val types: List<TypeEntity>
)

data class AbilityEntity(
    val name: String
)

data class FormEntity(
    val name: String
)

data class TypeEntity(
    val name: String
)

