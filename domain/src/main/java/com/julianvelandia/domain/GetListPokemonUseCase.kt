package com.julianvelandia.domain

import javax.inject.Inject

class GetListPokemonUseCase @Inject constructor(
    private val repository: PokeDexRepository
) {
   suspend operator fun invoke() = repository.getListPokemon()
}

