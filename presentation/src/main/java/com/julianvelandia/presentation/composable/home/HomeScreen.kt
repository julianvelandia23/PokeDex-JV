package com.julianvelandia.presentation.composable.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.julianvelandia.presentation.R
import com.julianvelandia.presentation.composable.EmptyState
import com.julianvelandia.presentation.composable.LoadingState
import com.julianvelandia.presentation.dimenXSmall16
import com.julianvelandia.presentation.dimenXSmall24
import com.julianvelandia.presentation.dimenXxxMedium48
import com.julianvelandia.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()

    var searchQuery by rememberSaveable { mutableStateOf("") }

    when {
        state.isLoading -> {
            LoadingState()
        }

        state.isError -> {
            EmptyState(value = stringResource(R.string.error))
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimenXSmall16)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChanged = {
                        searchQuery = it
                        viewModel.updateSearchQuery(it)
                    }
                )
                if (state.data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = dimenXSmall16),
                        verticalArrangement = Arrangement.spacedBy(dimenXSmall24)
                    ) {
                        items(state.data) { item ->
                            ItemPokemonHome(item) {
                                navigateTo(item.name)
                            }
                        }
                    }
                } else {
                    EmptyState(value = stringResource(R.string.empty_result))
                }
            }
        }
    }
}