package com.example.sweatmovies.ui.home.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.network.MovieDBService.Categories.NowPlaying
import com.example.sweatmovies.network.MovieDBService.Categories.Popular
import com.example.sweatmovies.network.MovieDBService.Categories.TopRated
import com.example.sweatmovies.network.MovieDBService.Categories.Upcoming
import com.example.sweatmovies.repositories.MovieCategoriesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import javax.inject.Inject

class GetMoviesByCategoryUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val categoriesRepository: MovieCategoriesRepository
) {
    suspend fun getPopularMovies() = getMoviesByCategory(Popular)

    suspend fun getNowPlaying()  = getMoviesByCategory(NowPlaying)

    suspend fun getTopRated()  = getMoviesByCategory(TopRated)

    suspend fun getUpcoming()  = getMoviesByCategory(Upcoming)

    private suspend fun getMoviesByCategory(category: MovieDBService.Categories): List<Movie> {
        //we always fetch and that will update internally de movies in the DB
        val networkResult = when(category) {
            Popular -> moviesRepository.fetchPopularMovies()
            NowPlaying -> moviesRepository.fetchNowPlayingMovies()
            TopRated -> moviesRepository.fetchTopRatedMovies()
            Upcoming -> moviesRepository.fetchUpcomingMovies()
        }

        return when (networkResult) {
            is NetworkResult.Error -> {
                val categories = categoriesRepository.getCategories()
                val localIds = when(category) {
                    Popular -> categories.popular
                    NowPlaying -> categories.nowPlaying
                    TopRated -> categories.topRated
                    Upcoming -> categories.upComing
                }
                val localMovies = when (category) {
                    Popular -> moviesRepository.getLocalPopular(localIds)
                    NowPlaying, TopRated, Upcoming -> moviesRepository.getRecentSix(localIds)
                }
                //if the request failed then the best effort is to emit local
                localMovies
            }
            is NetworkResult.Success -> {
                //update the categories so the next offline will have the categories updated
                val movies = networkResult.data.results
                val ids = movies.map { it.id }
                categoriesRepository.update(ids, category)
                return movies
            }
        }
    }

}