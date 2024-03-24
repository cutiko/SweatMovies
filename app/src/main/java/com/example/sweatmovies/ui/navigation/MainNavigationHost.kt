package com.example.sweatmovies.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = MainDestination.Home.destinationName
    ) {
        composable(Home.name) {
            PopularMoviesCarrousel {
                navController.navigate(Details.name)
            }
        }
        composable(Details.name) {
            MovieDetailsScreens()
        }

        composable(Search.name) {
            SearchScreen()
        }

        composable(WatchList.name) {
            WatchListScreen()
        }
    }
}

sealed class MainDestination(
    type: MainNavigationDestinations = MainNavigationDestinations.Home,
) {

    val destinationName = type.name
    data object Home : MainDestination(MainNavigationDestinations.Home), Tab {
        override val title = R.string.bottom_bar_home
        override val destination = destinationName
    }
    data object Search : MainDestination(MainNavigationDestinations.Search), Tab {
        override val title = R.string.bottom_bar_search
        override val destination = destinationName
    }
    data object WatchList : MainDestination(MainNavigationDestinations.WatchList), Tab {
        override val title = R.string.bottom_bar_watch_list
        override val destination = destinationName
    }
    data object Details : MainDestination(MainNavigationDestinations.Details)

    interface Tab {
        @get:StringRes
        val title: Int
        val destination: String
    }
}
enum class MainNavigationDestinations {
    Home,
    Details,
    Search,
    WatchList
}
