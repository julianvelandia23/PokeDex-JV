package com.julianvelandia.pokedexjv

object Route {
    const val HOME = "home"
    const val DETAILS = "details/{pokemonName}"

    fun getDetailsRoute(pokemonName: String): String {
        return "details/$pokemonName"
    }
}