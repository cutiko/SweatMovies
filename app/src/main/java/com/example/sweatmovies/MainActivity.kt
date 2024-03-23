package com.example.sweatmovies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.network.RetrofitProvider
import com.example.sweatmovies.sources.NetworkResult
import com.example.sweatmovies.sources.movies.MoviesNetworkSource
import com.example.sweatmovies.ui.theme.SweatMoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var moviesNetworkSource: MoviesNetworkSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SweatMoviesTheme {
                val coroutineScope = rememberCoroutineScope()
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                when (val result = moviesNetworkSource.getPopular()) {
                                    is NetworkResult.Error -> Log.d("CUTIKO_TAG", "$result")
                                    is NetworkResult.Success -> {
                                        result.data.results.forEach {
                                            Log.d("CUTIKO_TAG", "$it")
                                        }

                                    }
                                }
                            }
                        }
                    ) {
                        Text(text = "request")
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