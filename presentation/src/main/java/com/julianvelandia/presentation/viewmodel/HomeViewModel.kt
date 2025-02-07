package com.julianvelandia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianvelandia.domain.GetListPokemonUseCase
import com.julianvelandia.domain.GetSearchPokemonUseCase
import com.julianvelandia.domain.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = true,
    val data: List<Pokemon> = emptyList(),
    val isError: Boolean = false
)

@HiltViewModel
class HomeViewModel  @Inject constructor(
  getListPokemon: GetListPokemonUseCase,
  searchPokemon: GetSearchPokemonUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val _allPokemonState: StateFlow<HomeState> = getListPokemon.invoke()
        .map { result ->
            when {
                result.isSuccess -> {
                    val data = result.getOrNull()
                    HomeState(
                        isLoading = false,
                        data = data.orEmpty(),
                        isError = false
                    )
                }
                result.isFailure -> {
                    HomeState(
                        isLoading = false,
                        data = emptyList(),
                        isError = true
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

    val uiState = combine(_allPokemonState, _searchQuery) { state, query ->
        if (query.isEmpty()) {
            state
        } else {
            val filteredData = searchPokemon.invoke(query).getOrDefault(emptyList())
            state.copy(data = filteredData, isError = false)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        HomeState()
    )
}