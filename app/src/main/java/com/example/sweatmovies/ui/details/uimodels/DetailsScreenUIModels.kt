package com.example.sweatmovies.ui.details.uimodels

import com.example.sweatmovies.models.Movie

data class DetailsScreenState(
    val title: String = "",
    val photo: String? = "",
    val overview: String = "",
    val isLoadingTrailer: Boolean = false,
    val isFavorite: Boolean = false
) {
    companion object {
        val default = DetailsScreenState()

        fun DetailsScreenState.make(movie: Movie, isFavorite: Boolean): DetailsScreenState {
            return this.copy(
                title = movie.title,
                photo = movie.poster(),
                overview = movie.overview,
                isFavorite = isFavorite
            )
        }
        fun DetailsScreenState.loadingTrailer(isLoading: Boolean): DetailsScreenState {
            return this.copy(isLoadingTrailer = isLoading)
        }
    }
}