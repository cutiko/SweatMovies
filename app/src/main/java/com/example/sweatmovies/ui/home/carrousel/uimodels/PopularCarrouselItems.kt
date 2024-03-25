package com.example.sweatmovies.ui.home.carrousel.uimodels


import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase

private typealias MovieModel = Movie
sealed class PopularCarrouselItem(open val id: Int) {
    data object Loading : PopularCarrouselItem(-1)
    data class Movie(
        override val id: Int = 0,
        val photo: String? = null,
        val position: Int = 0
    ) : PopularCarrouselItem(id)

    companion object {
        val default: List<PopularCarrouselItem> = listOf(Loading)
        fun List<PopularCarrouselItem>.update(
            movies: List<MovieModel>
        ): List<PopularCarrouselItem> {
            return movies.mapIndexed { index, movie ->
                Movie(
                    id = movie.id,
                    photo = movie.poster(),
                    position = index + 1
                )
            }
        }
    }
}