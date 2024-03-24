package com.example.sweatmovies.ui.search.uimodels

import com.example.sweatmovies.models.MovieOverview

sealed class SearchResultItem(open val id: Int) {
    data object Loading: SearchResultItem(-1)

    data class Overview(
        override val id: Int = 0,
        val name: String = "",
        val photo: String? = null
    ): SearchResultItem(id)

    companion object {
        val default: List<SearchResultItem> = listOf(Loading)

        fun List<SearchResultItem>.update(overviews: List<MovieOverview>): List<SearchResultItem> {
            return overviews.map {
                Overview(
                    id = it.id,
                    name = it.name,
                    photo = it.poster()
                )
            }
        }
    }
}