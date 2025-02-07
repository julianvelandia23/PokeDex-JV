package com.julianvelandia.domain

import javax.inject.Inject

class GetSearchPokemonUseCase @Inject constructor(
    private val repository: PokeDexRepository
) {
    suspend operator fun invoke(query: String) = repository.searchPokemon(query)
}

