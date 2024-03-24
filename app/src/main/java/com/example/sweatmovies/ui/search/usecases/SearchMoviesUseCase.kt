package com.example.sweatmovies.ui.search.usecases

import com.example.sweatmovies.models.MovieOverview
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    private val softCache = mutableMapOf<String, List<MovieOverview>>()
    suspend fun byTerm(term: String): List<MovieOverview> {
        softCache[term]?.let {
            return it
        }
        val result = moviesRepository.searchMovies(term)
        val overviews = when(result) {
            is NetworkResult.Error -> emptyList()
            is NetworkResult.Success -> result.data.results
        }
        softCache[term] = overviews
        return overviews
    }

}