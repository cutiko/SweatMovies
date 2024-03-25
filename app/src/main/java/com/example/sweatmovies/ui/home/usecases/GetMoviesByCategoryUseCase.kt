package com.example.sweatmovies.ui.home.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.network.MovieDBService.Categories.*
import com.example.sweatmovies.repositories.MovieCategoriesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesByCategoryUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val categoriesRepository: MovieCategoriesRepository
) {

    fun observePopularMovies() = observeByCategory(Popular)

    fun observeNowPlaying()  = observeByCategory(NowPlaying)

    fun observeTopRated()  = observeByCategory(TopRated)

    fun observeUpcoming()  = observeByCategory(Upcoming)

    private fun observeByCategory(category: MovieDBService.Categories) = flow {
        val categories = categoriesRepository.getCategories()
        val localIds = when(category) {
            Popular -> categories.popular
            NowPlaying -> categories.nowPlaying
            TopRated -> categories.topRated
            Upcoming -> categories.upComing
        }
        val localMovies = if (category.limitToSix()) {
            moviesRepository.getRecentSix(localIds)
        } else {
            moviesRepository.getLocalPopular(localIds)
        }

        //first time the user visit the app the movies are empty so we emit loading
        if (localMovies.isEmpty()) {
            emit(Result.Loading)
        } else {
            emit(Result.Success(localMovies))
        }

        //we always fetch and that will update internally de movies in the DB
        val networkResult = when(category) {
            Popular -> moviesRepository.fetchPopularMovies()
            NowPlaying -> moviesRepository.fetchNowPlayingMovies()
            TopRated -> moviesRepository.fetchTopRatedMovies()
            Upcoming -> moviesRepository.fetchUpcomingMovies()
        }

        when (networkResult) {
            is NetworkResult.Error -> {
                //if the request failed then the best effort is to emit local
                emit(Result.Success(localMovies))
            }
            is NetworkResult.Success -> {
                //update the categories so the next offline will have the categories updated
                val movies = networkResult.data.results
                val ids = movies.map { it.id }
                categoriesRepository.update(ids, category)
                emit(Result.Success(movies))
            }
        }
    }

    private fun MovieDBService.Categories.limitToSix() : Boolean {
        return this != MovieDBService.Categories.Popular
    }

    sealed class Result {
        data class Success(
            val movies: List<Movie> = emptyList()
        ) : Result()
        data object Loading : Result()
    }

}