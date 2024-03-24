package com.example.sweatmovies.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sweatmovies.ui.search.composables.SearchBar
import com.example.sweatmovies.ui.search.composables.SearchResultsList

@Composable
fun SearchScreen(
    onItemClicked: (Int) -> Unit
) {
    val viewModel: SearchMovieViewModel = hiltViewModel()
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SearchBar(userInput = state.userInput) {
            viewModel.search(it)
        }

        SearchResultsList(items = state.results, onItemClicked = onItemClicked)
    }
}