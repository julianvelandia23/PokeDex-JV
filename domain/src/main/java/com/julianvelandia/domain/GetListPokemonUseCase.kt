package com.julianvelandia.domain

import javax.inject.Inject

class GetListPokemonUseCase @Inject constructor(
    private val repository: PokeDexRepository
) {
    operator fun invoke() = repository.getListPokemon()
}

