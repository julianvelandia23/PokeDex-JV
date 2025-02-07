package com.julianvelandia.data.local

import com.julianvelandia.domain.Pokemon


fun HomePokemonEntity.toDomain() = Pokemon(name.orEmpty())

fun Pokemon.toEntity() = HomePokemonEntity(name = name)