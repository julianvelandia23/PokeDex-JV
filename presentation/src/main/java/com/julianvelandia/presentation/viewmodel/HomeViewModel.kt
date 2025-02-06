package com.julianvelandia.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvelandia.domain.GetListPokemonUseCase
import com.julianvelandia.domain.PokeDexRepository
import com.julianvelandia.domain.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = true,
    val data: List<Pokemon>? = emptyList(),
    val errorMessage: String = ""
)

@HiltViewModel
class HomeViewModel  @Inject constructor(
  private val getListPokemon: GetListPokemonUseCase
) : ViewModel() {


    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState


    fun getDogs() = viewModelScope.launch {
        _homeState.value = _homeState.value.copy(isLoading = true)
        val result = getListPokemon.invoke()
        _homeState.value = if (result.isSuccess) {
            _homeState.value.copy(
                isLoading = false,
                data = result.getOrNull(),
                errorMessage = ""
            )
        } else {
            _homeState.value.copy(
                isLoading = false,
                errorMessage = result.exceptionOrNull()?.message.orEmpty()
            )
        }
    }

}