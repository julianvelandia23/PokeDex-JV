package com.julianvelandia.domain

data class PokemonDetail(
    val name: String,
    val image: String,
    val abilities: List<Abilities>,
    val forms: List<Forms>,
    val types: List<Types>
)

data class Abilities(val ability: Ability)
data class Ability(val name: String)


data class Forms(
    val name: String
)

data class Types(val type: Type)
data class Type(val name: String)