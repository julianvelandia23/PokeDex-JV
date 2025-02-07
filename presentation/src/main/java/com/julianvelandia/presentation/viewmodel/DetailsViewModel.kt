package com.julianvelandia.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvelandia.domain.GetDetailPokemonUseCase
import com.julianvelandia.domain.Pokemon
import com.julianvelandia.domain.PokemonDetail
import com.julianvelandia.presentation.NavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val data: PokemonDetail? = null,
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    getDetailPokemon: GetDetailPokemonUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonName: String? = savedStateHandle[NavArguments.POKEMON_NAME]

    val detailState: StateFlow<DetailState> = getDetailPokemon(pokemonName.orEmpty())
        .map { result ->
            if (result.isSuccess) {
                DetailState(isLoading = false, data = result.getOrNull())
            } else {
                DetailState(isLoading = false, isError = true)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DetailState()
        )

}
