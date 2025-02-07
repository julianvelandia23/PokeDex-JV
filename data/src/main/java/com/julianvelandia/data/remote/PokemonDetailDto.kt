package com.julianvelandia.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val abilities: List<AbilitiesDto>,
    val forms: List<FormsDto>,
    val types: List<TypesDto>
)


@JsonClass(generateAdapter = true)
data class AbilitiesDto(
   val  ability: AbilityDto
)

@JsonClass(generateAdapter = true)
data class AbilityDto(
    val name: String
)

@JsonClass(generateAdapter = true)
data class FormsDto(
    val name: String
)

@JsonClass(generateAdapter = true)
data class TypesDto(
   val  type: TypeDto
)

@JsonClass(generateAdapter = true)
data class TypeDto(
   val name: String
)