package com.example.sweatmovies.ui.details.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.repositories.FavoritesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository
) {
    suspend fun getTrailer(id: Int) = moviesRepository.getTrailer(id)
    fun observeMovie(id: Int) = favoritesRepository.observeFavorite(id).map { isFavorite ->
        val movie = moviesRepository.getMovie(id)
        MovieAndFavorite(movie, isFavorite)
    }
    suspend fun updateFavorite(
        isFavorite: Boolean,
        id: Int
    ) = favoritesRepository.update(isFavorite, id)

    data class MovieAndFavorite(
        val movie: Movie? = null,
        val favorite: Boolean = false
    )

}