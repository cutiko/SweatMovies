package com.example.sweatmovies.ui.search.uimodels

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.uimodels.MovieResultItem
import com.example.sweatmovies.ui.uimodels.MovieResultItem.Companion.addLoading
import com.example.sweatmovies.ui.uimodels.MovieResultItem.Companion.update

data class SearchResultsScreenState(
    val userInput: String = "",
    val results: List<MovieResultItem> = MovieResultItem.default
) {
    companion object {
        val default = SearchResultsScreenState()
        fun SearchResultsScreenState.updateUserInput(userInput: String): SearchResultsScreenState {
            return this.copy(
                userInput = userInput,
                results = results.addLoading() //searching also means loading
            )
        }

        fun SearchResultsScreenState.updateResults(overviews: List<Movie>): SearchResultsScreenState {
            val items = this.results.update(overviews)
            return this.copy(results = items)
        }
    }
}