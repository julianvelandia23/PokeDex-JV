package com.julianvelandia.presentation.composable.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.julianvelandia.domain.PokemonDetail
import com.julianvelandia.presentation.R
import com.julianvelandia.presentation.dimenXSmall16
import com.julianvelandia.presentation.dimenXxxMedium64

@Composable
fun DetailState(
    modifier: Modifier = Modifier,
    pokemonDetail: PokemonDetail
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pokemonDetail.image)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .build()
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimenXSmall16),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimenXxxMedium64)
                .padding(bottom = dimenXSmall16)
                .clip(CircleShape)
        )

        Text(
            text = pokemonDetail.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = dimenXSmall16)
        )


        Text(
            text = stringResource(R.string.abilities),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = dimenXSmall16)
        )
        pokemonDetail.abilities.forEach { ability ->
            Text(
                text = stringResource(R.string.value, ability.ability.name),
                style = MaterialTheme.typography.bodyMedium
            )
        }


        Text(
            text = stringResource(R.string.types),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = dimenXSmall16)
        )
        pokemonDetail.types.forEach { type ->
            Text(
                text = stringResource(R.string.value, type.type.name),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            text = stringResource(R.string.forms),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = dimenXSmall16)
        )
        pokemonDetail.forms.forEach { form ->
            Text(
                text = stringResource(R.string.value, form.name),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}