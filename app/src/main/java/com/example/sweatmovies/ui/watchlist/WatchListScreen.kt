package com.example.sweatmovies.ui.watchlist

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sweatmovies.ui.composables.MoviesResultsList

@Composable
fun WatchListScreen(
    onMovieClicked: (Int) -> Unit
) {
    val viewModel: WatchListViewModel = hiltViewModel()
    val items by viewModel.items.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.observeFavorites()
    }

    MoviesResultsList(
        modifier = Modifier.padding(16.dp),
        items = items,
        onItemClicked = onMovieClicked
    )
}