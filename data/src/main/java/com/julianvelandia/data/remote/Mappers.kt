package com.julianvelandia.data.remote

import com.julianvelandia.domain.Abilities
import com.julianvelandia.domain.Ability
import com.julianvelandia.domain.Forms
import com.julianvelandia.domain.Pokemon
import com.julianvelandia.domain.PokemonDetail
import com.julianvelandia.domain.Type
import com.julianvelandia.domain.Types

//Home
fun PokemonDto.toDomain() = Pokemon(name)

//Details
fun PokemonDetailDto.toDomain() = PokemonDetail(
    name = name,
    image = POKEMON_IMAGE_URL.format(id),
    abilities = abilities.map { it.toDomain() },
    forms = forms.map { it.toDomain() },
    types = types.map { it.toDomain() }
)

fun AbilitiesDto.toDomain() = Abilities(
    ability = ability.toDomain()
)

fun AbilityDto.toDomain() = Ability(name)

fun FormsDto.toDomain() = Forms(
    name = name,
)

fun TypesDto.toDomain() = Types(
    type = type.toDomain()
)

fun TypeDto.toDomain() = Type(name)

private const val POKEMON_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%s.png"

