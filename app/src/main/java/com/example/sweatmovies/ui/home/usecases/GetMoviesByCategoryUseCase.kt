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
            is NetworkResult.Error -> Unit //maybe offline (not delete locals)
            is NetworkResult.Success -> {
                //update the categories so the next offline will have the categories updated
                val ids = networkResult.data.results.map { it.id }
                categoriesRepository.updatePopular(ids)
            }
        }
        //now that the ids and the movies are updated we can join them and show them to the UI
        //if the request above failed then we still show them what is on the DB
        val updatedPopularIds = categoriesRepository.getCategories().popular
        val updatedPopularMovies = moviesRepository.getLocalPopular(updatedPopularIds)
        emit(Result.Success(updatedPopularMovies))
    }

    sealed class Result {
        data class Success(
            val movies: List<Movie> = emptyList()
        ) : Result()
        data object Loading : Result()
    }

}