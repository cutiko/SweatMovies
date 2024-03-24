package com.example.sweatmovies.ui.watchlist

import com.example.sweatmovies.repositories.FavoritesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchListUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository
) {
    fun observeFavorites() = favoritesRepository.observeFavorites().map { ids ->
        moviesRepository.getMoviesById(ids)
    }

}