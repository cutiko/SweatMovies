package com.example.sweatmovies.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sweatmovies.ui.details.composables.MovieDetailsContent
import com.example.sweatmovies.ui.details.composables.MovieDetailsTopBar
import kotlinx.coroutines.launch

@Composable
fun MovieDetailsScreens(movieId: Int, backNavigation: ()-> Unit) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.observeMovie(movieId)
    }

    Scaffold(
        modifier = Modifier.padding(8.dp),
        topBar = { MovieDetailsTopBar(backNavigation) }
    ) { paddingValues ->
        MovieDetailsContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            posterClicked = {
                coroutineScope.launch {
                    val trailer = viewModel.getTrailer(movieId) ?: return@launch
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse(trailer))
                    context.startActivity(intent)
                }
            },
            onFavoriteClicked = { isFavorite ->
                viewModel.updateFavorite(isFavorite, movieId)
            }
        )
    }
}