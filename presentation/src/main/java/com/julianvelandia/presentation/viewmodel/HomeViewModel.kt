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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = true,
    val data: List<Pokemon>? = emptyList(),
    val errorMessage: String = ""
)

@HiltViewModel
class HomeViewModel  @Inject constructor(
  getListPokemon: GetListPokemonUseCase
) : ViewModel() {

    val homeState: StateFlow<HomeState> = getListPokemon.invoke()
        .map { result ->
            when {
                result.isSuccess -> {
                    val data = result.getOrNull()
                    HomeState(
                        isLoading = false,
                        data = data ?: emptyList(),
                        errorMessage = ""
                    )
                }
                result.isFailure -> {
                    val errorMessage = result.exceptionOrNull()?.message.orEmpty()
                    HomeState(
                        isLoading = false,
                        data = emptyList(),
                        errorMessage = errorMessage
                    )
                }
                else -> {
                    HomeState(isLoading = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            HomeState()
        )

}