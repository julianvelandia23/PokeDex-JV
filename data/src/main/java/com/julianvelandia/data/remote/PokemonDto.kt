package com.julianvelandia.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDto(
    val name: String
)