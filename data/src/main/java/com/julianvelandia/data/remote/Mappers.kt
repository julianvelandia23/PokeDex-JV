package com.julianvelandia.data.remote

import com.julianvelandia.domain.Pokemon

fun PokemonDto.toDomain() = Pokemon(
    name
)