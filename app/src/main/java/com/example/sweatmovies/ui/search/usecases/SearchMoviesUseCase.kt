package com.example.sweatmovies.ui.search.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.repositories.MoviesRepository
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
        val movies = moviesRepository.searchMovies(term)
        softCache[term] = movies
        return movies
    }

}