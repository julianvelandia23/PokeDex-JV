package com.julianvelandia.data.local.detail

import com.julianvelandia.data.POKEMON_IMAGE_URL
import com.julianvelandia.data.remote.PokemonDetailDto
import com.julianvelandia.domain.Abilities
import com.julianvelandia.domain.Ability
import com.julianvelandia.domain.Forms
import com.julianvelandia.domain.PokemonDetail
import com.julianvelandia.domain.Type
import com.julianvelandia.domain.Types

fun DetailPokemonEntity.toDomain(): PokemonDetail {
    return PokemonDetail(
        name = name,
        image = image,
        abilities = abilities.map { Abilities(Ability(it.name)) },
        forms = forms.map { Forms(it.name) },
        types = types.map { Types(Type(it.name)) }
    )
}

fun PokemonDetailDto.toEntity(): DetailPokemonEntity {
    return DetailPokemonEntity(
        id = id,
        name = name,
        image = POKEMON_IMAGE_URL.format(id),
        abilities = abilities.map { AbilityEntity(name = it.ability.name) },
        forms = forms.map { FormEntity(name = it.name) },
        types = types.map { TypeEntity(name = it.type.name) }
    )
}