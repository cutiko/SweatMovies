package com.example.sweatmovies.ui.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.sweatmovies.ui.home.carrousel.composables.PopularMoviesCarrousel
import com.example.sweatmovies.ui.home.tabs.composables.HomeGridSection

@Composable
fun HomeTab(onItemClicked: (Int)-> Unit) {
    Column {
        PopularMoviesCarrousel(onItemClicked)
        HomeGridSection(onItemClicked)
    }
}