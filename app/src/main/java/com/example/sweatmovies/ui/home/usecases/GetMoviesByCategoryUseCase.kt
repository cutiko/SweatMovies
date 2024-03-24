package com.example.sweatmovies.ui.home.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.repositories.MovieCategoriesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetMoviesByCategoryUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val categoriesRepository: MovieCategoriesRepository
) {

    fun observePopularMovies() = flow<Result> {
        val localPopularIds = categoriesRepository.getCategories().popular
        val localPopularMovies = moviesRepository.getLocalPopular(localPopularIds)
        //first time the user visit the app the movies are empty so we emit loading
        if (localPopularMovies.isEmpty()) {
            emit(Result.Loading)
        } else {
            emit(Result.Success(localPopularMovies))
        }
        //we always fetch and that will update internally de movies in the DB
        val networkResult = moviesRepository.fetchPopularMovies()
        when (networkResult) {
            is NetworkResult.Error -> {
                //if the request failed then the best effort is to emit local
                emit(Result.Success(localPopularMovies))
            }
            is NetworkResult.Success -> {
                //update the categories so the next offline will have the categories updated
                val movies = networkResult.data.results
                val ids = movies.map { it.id }
                categoriesRepository.updatePopular(ids)
                emit(Result.Success(movies))
            }
        }
    }

    sealed class Result {
        data class Success(
            val movies: List<Movie> = emptyList()
        ) : Result()
        data object Loading : Result()
    }

}