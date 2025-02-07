package com.julianvelandia.data.local

import com.julianvelandia.data.remote.PokemonDto
import com.julianvelandia.domain.Pokemon

fun PokemonDto.toEntity() = HomePokemonEntity(name = name)

fun HomePokemonEntity.toDomain() = Pokemon(name.orEmpty())

fun Pokemon.toEntity() = HomePokemonEntity(name = name)