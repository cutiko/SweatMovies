package com.example.sweatmovies.ui.home.tabs.uimodels

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeTabs.NowPlaying
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeTabs.TopRated
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeTabs.Upcoming

data class HomeGridState(
    val selectedTab: HomeTabs = NowPlaying,
    val nowPlaying: List<MovieTile> = emptyList(),
    val upcoming: List<MovieTile> = emptyList(),
    val topRated: List<MovieTile> = emptyList(),
    val movies: List<MovieTile> = emptyList()
) {
    companion object {
        val default = HomeGridState()
        fun HomeGridState.nowPlaying(movies: List<Movie>): HomeGridState {
            val tiles = movies.toTiles()
            return this.copy(
                nowPlaying = tiles,
                movies = if (selectedTab == NowPlaying) tiles.take(6) else this.movies
            )
        }

        fun HomeGridState.upcoming(movies: List<Movie>): HomeGridState {
            val tiles = movies.toTiles()
            return this.copy(
                upcoming = tiles,
                movies = if (selectedTab == Upcoming) tiles.take(6) else this.movies
            )
        }

        fun HomeGridState.topRated(movies: List<Movie>): HomeGridState {
            val tiles = movies.toTiles()
            return this.copy(
                topRated = tiles,
                movies = if (selectedTab == TopRated) tiles.take(6) else this.movies
            )
        }

        fun HomeGridState.selectTab(tab: HomeTabs): HomeGridState {
            val movies = when(tab) {
                NowPlaying -> this.nowPlaying.take(6)
                Upcoming -> this.upcoming.take(6)
                TopRated -> this.topRated.take(6)
            }
            return this.copy(
                selectedTab = tab,
                movies = movies
            )
        }
        private fun List<Movie>.toTiles() = this.map {
            MovieTile(
                id = it.id,
                photo = it.poster(),
                name = it.title
            )
        }
    }
}
data class MovieTile(
    val id: Int = 0,
    val photo: String? = null,
    val name: String = ""
)