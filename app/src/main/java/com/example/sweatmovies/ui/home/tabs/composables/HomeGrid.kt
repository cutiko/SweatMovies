package com.example.sweatmovies.ui.home.tabs.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sweatmovies.ui.home.tabs.uimodels.MovieTile

@Composable
fun HomeGrid(
    items: List<MovieTile>,
    onItemClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxHeight(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id }
        ) { index ->
            TileView(
                movieTile = items[index],
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
private fun TileView(
    movieTile: MovieTile,
    onItemClicked: (Int) -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movieTile.photo)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClicked(movieTile.id) }
    )
}