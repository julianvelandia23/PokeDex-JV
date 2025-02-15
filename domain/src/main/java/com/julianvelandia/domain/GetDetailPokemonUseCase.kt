package com.julianvelandia.domain

import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val repository: PokeDexRepository
) {
    operator fun invoke(name: String) = repository.getPokemonDetail(name)
}

