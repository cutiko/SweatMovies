package com.example.sweatmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.sweatmovies.ui.navigation.BottomMenu
import com.example.sweatmovies.ui.navigation.MainNavigationHost
import com.example.sweatmovies.ui.theme.SweatMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SweatMoviesTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    bottomBar = { BottomMenu(navController) }
                ) { paddingValues ->
                    MainNavigationHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController
                    )
                }
            }
        }
    }
}