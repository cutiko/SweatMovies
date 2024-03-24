package com.example.sweatmovies.ui.search.uimodels

import com.example.sweatmovies.models.MovieOverview
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.addLoading
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.update


data class SearchResultsScreenState(
    val userInput: String = "",
    val results: List<SearchResultItem> = SearchResultItem.default
) {
    companion object {
        val default = SearchResultsScreenState()
        fun SearchResultsScreenState.updateUserInput(userInput: String): SearchResultsScreenState {
            return this.copy(
                userInput = userInput,
                results = results.addLoading() //searching also means loading
            )
        }

        fun SearchResultsScreenState.updateResults(overviews: List<MovieOverview>): SearchResultsScreenState {
            val items = this.results.update(overviews)
            return this.copy(results = items)
        }
    }
}
sealed class SearchResultItem(open val id: Int) {
    data object Loading: SearchResultItem(-1)

    data class Overview(
        override val id: Int = 0,
        val name: String = "",
        val photo: String? = null
    ): SearchResultItem(id)

    companion object {
        val default: List<SearchResultItem> = emptyList()

        fun List<SearchResultItem>.update(overviews: List<MovieOverview>): List<SearchResultItem> {
            return overviews.map {
                Overview(
                    id = it.id,
                    name = it.name,
                    photo = it.poster()
                )
            }
        }

        fun List<SearchResultItem>.addLoading(): List<SearchResultItem> {
            return when {
                this.isEmpty() -> listOf(Loading)
                this.first() is Loading -> this
                else -> listOf(Loading) + this
            }
        }
    }
}