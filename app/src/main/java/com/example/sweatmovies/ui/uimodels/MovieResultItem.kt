package com.example.sweatmovies.ui.uimodels

import com.example.sweatmovies.models.Movie


sealed class MovieResultItem(open val id: Int) {
    data object Loading: MovieResultItem(-1)

    data class Overview(
        override val id: Int = 0,
        val name: String = "",
        val photo: String? = null
    ): MovieResultItem(id)

    companion object {
        val default: List<MovieResultItem> = emptyList()

        fun List<MovieResultItem>.update(overviews: List<Movie>): List<MovieResultItem> {
            return overviews.map {
                Overview(
                    id = it.id,
                    name = it.title,
                    photo = it.poster()
                )
            }
        }

        fun List<MovieResultItem>.addLoading(): List<MovieResultItem> {
            return when {
                this.isEmpty() -> listOf(Loading)
                this.first() is Loading -> this
                else -> listOf(Loading) + this
            }
        }
    }
}