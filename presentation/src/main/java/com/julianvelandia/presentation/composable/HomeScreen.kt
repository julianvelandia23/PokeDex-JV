package com.julianvelandia.presentation.composable

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.julianvelandia.presentation.R
import com.julianvelandia.presentation.dimenXSmall16
import com.julianvelandia.presentation.dimenXxxMedium48
import com.julianvelandia.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit = {}
) {

    val state by viewModel.homeState.collectAsState()

    when {
        state.isLoading -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        state.data.isNullOrEmpty().not() -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = dimenXxxMedium48, horizontal = dimenXSmall16),
                verticalArrangement = Arrangement.spacedBy(dimenXSmall16)
            ) {
                state.data?.let { items ->
                    items(items) { item ->
                        ItemPokemonHome(item) {
                            navigateTo(item.name)
                        }
                    }
                }
            }
        }

        else -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(dimenXSmall16)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.error),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}