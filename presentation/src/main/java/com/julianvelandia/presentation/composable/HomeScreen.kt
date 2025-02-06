package com.julianvelandia.presentation.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateTo: (String) -> Unit = {}
) {
    Text(
        text = "Hello ",
        modifier = modifier
    )
}