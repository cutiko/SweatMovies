package com.example.sweatmovies.ui.home.carrousel.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sweatmovies.ui.composables.CircularLoading
import com.example.sweatmovies.ui.home.carrousel.PopularMoviesViewModel
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem

@Composable
fun PopularMoviesCarrousel(
    onItemClicked: (Int)-> Unit
) {
    val popularMoviesViewModel: PopularMoviesViewModel = hiltViewModel()
    val movies by popularMoviesViewModel.carrouselItems.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        popularMoviesViewModel.getMovies()
    }
    LazyRow {
        items(count = movies.size, key = { index -> movies[index].id }) { index ->
            when(val movie = movies[index]) {
                PopularCarrouselItem.Loading -> {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularLoading()
                    }
                }
                is PopularCarrouselItem.Movie -> MovieCarrouselItem(movie, onItemClicked)
            }
        }
    }
}