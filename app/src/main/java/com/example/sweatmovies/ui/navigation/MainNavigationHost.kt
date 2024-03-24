package com.example.sweatmovies.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sweatmovies.R
import com.example.sweatmovies.ui.search.SearchScreen
import com.example.sweatmovies.ui.details.MovieDetailsScreens
import com.example.sweatmovies.ui.home.carrousel.composables.PopularMoviesCarrousel
import com.example.sweatmovies.ui.navigation.MainNavigationDestinations.*
import com.example.sweatmovies.ui.watchlist.WatchListScreen

@Composable
fun MainNavigationHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainNavigationDestinations.Home.name
    ) {
        composable(Home.name) {
            PopularMoviesCarrousel {
                navController.navigate("${Details.name}/$it")
            }
        }

        composable(Search.name) {
            SearchScreen()
        }

        composable(WatchList.name) {
            WatchListScreen()
        }

        composable(
            route = "${Details.name}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailsScreens(movieId)
        }
    }
}

enum class MainTab(@StringRes val title: Int, val destination: MainNavigationDestinations) {
    Home(R.string.bottom_bar_home, MainNavigationDestinations.Home),
    Search(R.string.bottom_bar_search, MainNavigationDestinations.Search),
    WatchList(R.string.bottom_bar_watch_list, MainNavigationDestinations.WatchList)
}
enum class MainNavigationDestinations {
    Home,
    Details,
    WatchList,
    Search
}
