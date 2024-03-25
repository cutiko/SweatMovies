package com.example.sweatmovies.ui.home.tabs.composables

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sweatmovies.ui.home.tabs.HomeGridViewModel

@Composable
fun HomeGridSection(onItemClicked: (Int)-> Unit) {
    val viewModel: HomeGridViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.observeMovies()
    }

    HomeTabs(selectedTab = state.selectedTab) {
        viewModel.selectTab(it)
    }
    Log.d("CUTIKO_TAG", "nowPlaying ${state.nowPlaying}")
    Log.d("CUTIKO_TAG", "upcoming ${state.upcoming}")
    Log.d("CUTIKO_TAG", "top rated ${state.topRated}")
    Log.d("CUTIKO_TAG", "movies ${state.movies}")
    HomeGrid(
        items = state.movies,
        onItemClicked = onItemClicked
    )
}