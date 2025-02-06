package com.julianvelandia.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.julianvelandia.domain.Pokemon
import com.julianvelandia.presentation.R
import com.julianvelandia.presentation.dimenXSmall16

@Composable
fun ItemPokemonHome(item: Pokemon, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.name,
                maxLines = 1,
                modifier = Modifier.padding(start = dimenXSmall16)
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = stringResource(R.string.accessibility_see_details),
            modifier = Modifier.padding(end = dimenXSmall16)
        )
    }
}