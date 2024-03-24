package com.example.sweatmovies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sweatmovies.ui.details.MovieDetailsScreens
import com.example.sweatmovies.ui.home.carrousel.composables.PopularMoviesCarrousel

@Composable
fun MainNavigationHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = MainNavigationDestinations.Home.name) {
        composable(MainNavigationDestinations.Home.name) {
            PopularMoviesCarrousel {
                navController.navigate(MainNavigationDestinations.Details.name)
            }
        }
        composable(MainNavigationDestinations.Details.name) {
            MovieDetailsScreens()
        }
    }
}

enum class MainNavigationDestinations {
    Home,
    Details
}
