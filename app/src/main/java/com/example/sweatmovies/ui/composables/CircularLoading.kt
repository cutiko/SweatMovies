package com.example.sweatmovies.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularLoading() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp)
    )
}

@Composable
fun HorizontalLoading() {
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth()
    )
}

