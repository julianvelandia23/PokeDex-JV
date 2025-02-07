package com.julianvelandia.data

import com.julianvelandia.data.local.detail.AbilityEntity
import com.julianvelandia.data.local.detail.DetailPokemonEntity
import com.julianvelandia.data.local.detail.FormEntity
import com.julianvelandia.data.local.detail.TypeEntity

val dummyDetailPokemon = DetailPokemonEntity(
    id = 6,
    name = "Charizard",
    image = "https://pokeapi.co/media/sprites/charizard.png",
    abilities = listOf(
        AbilityEntity(name = "Blaze"),
        AbilityEntity(name = "Solar Power")
    ),
    forms = listOf(
        FormEntity(name = "Charizard"),
        FormEntity(name = "Charizard-Mega-X"),
        FormEntity(name = "Charizard-Mega-Y")
    ),
    types = listOf(
        TypeEntity(name = "Fire"),
        TypeEntity(name = "Flying")
    )
)
