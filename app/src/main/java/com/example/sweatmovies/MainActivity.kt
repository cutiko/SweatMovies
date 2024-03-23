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
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.network.RetrofitProvider
import com.example.sweatmovies.ui.theme.SweatMoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var retrofit: Retrofit
    private val moviesService by lazy {
        retrofit.create(MovieDBService::class.java)
    }

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
                                val result = moviesService.getPopular()
                                Log.d("CUTIKO_TAG", "RESULT: $result")
                                Log.d("CUTIKO_TAG", "RESULT: ${result.body()?.results?.size}")
                                result.body()?.results?.forEach {
                                    Log.d("CUTIKO_TAG", "$it")
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