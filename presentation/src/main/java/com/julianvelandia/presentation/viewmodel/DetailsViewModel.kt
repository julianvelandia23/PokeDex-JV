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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailState(
    val isLoading: Boolean = true,
    val data: PokemonDetail? = null,
    val errorMessage: String = ""
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailPokemon: GetDetailPokemonUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonName: String? = savedStateHandle[NavArguments.POKEMON_NAME]

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> get() = _detailState

    init {
        pokemonName?.let {
           getPokemon(it)
        }
    }


    private fun getPokemon(name: String) = viewModelScope.launch {
        _detailState.value = _detailState.value.copy(isLoading = true)
        val result = getDetailPokemon.invoke(name)
        _detailState.value = if (result.isSuccess) {
            _detailState.value.copy(
                isLoading = false,
                data = result.getOrNull(),
                errorMessage = ""
            )
        } else {
            _detailState.value.copy(
                isLoading = false,
                errorMessage = result.exceptionOrNull()?.message.orEmpty()
            )
        }
    }
}
