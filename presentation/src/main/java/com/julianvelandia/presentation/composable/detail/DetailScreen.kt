package com.julianvelandia.presentation.composable.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.julianvelandia.presentation.R
import com.julianvelandia.presentation.composable.EmptyState
import com.julianvelandia.presentation.composable.LoadingState
import com.julianvelandia.presentation.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {

    val state by viewModel.detailState.collectAsState()

    when {
        state.isLoading -> {
            LoadingState()
        }

        state.isError -> {
            EmptyState()
        }

        else -> {
            if (state.data != null) {
                state.data?.let { pokemonDetail ->
                    DetailState(
                        modifier = modifier,
                        pokemonDetail = pokemonDetail
                    )
                }
            } else {
                EmptyState(value = stringResource(R.string.empty_result))
            }
        }
    }
}