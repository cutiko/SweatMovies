package com.example.sweatmovies.ui.search.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    private val softCache = mutableMapOf<String, List<Movie>>()
    suspend fun byTerm(term: String): List<Movie> {
        if (term.isBlank()) return emptyList()
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