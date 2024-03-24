package com.example.sweatmovies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import com.example.sweatmovies.ui.composables.Loading
import com.example.sweatmovies.ui.home.carrousel.PopularMoviesViewModel
import com.example.sweatmovies.ui.home.carrousel.composables.MovieCarrouselItem
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase
import com.example.sweatmovies.ui.theme.SweatMoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SweatMoviesTheme {
                val popularMoviesViewModel: PopularMoviesViewModel = viewModel()
                val movies by popularMoviesViewModel.carrouselItems.collectAsStateWithLifecycle()
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LaunchedEffect(key1 = Unit) {
                        popularMoviesViewModel.startObserving()
                    }
                    LazyRow {
                        items(count = movies.size, key = { index -> movies[index].id }) { index ->
                            when(val movie = movies[index]) {
                                PopularCarrouselItem.Loading -> Loading()
                                is PopularCarrouselItem.Movie -> MovieCarrouselItem(movie)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SweatMoviesTheme {
        Greeting("Android")
    }
}