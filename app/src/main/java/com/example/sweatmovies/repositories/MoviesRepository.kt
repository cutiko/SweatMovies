package com.example.sweatmovies.repositories

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MoviesResponse
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.sources.NetworkResult
import com.example.sweatmovies.sources.movies.MoviesLocalSource
import com.example.sweatmovies.sources.movies.MoviesRemoteSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesRepository {
    suspend fun fetchPopularMovies(): NetworkResult<MoviesResponse>

    suspend fun fetchNowPlayingMovies(): NetworkResult<MoviesResponse>

    suspend fun fetchTopRatedMovies(): NetworkResult<MoviesResponse>

    suspend fun fetchUpcomingMovies(): NetworkResult<MoviesResponse>

    suspend fun getLocalPopular(ids: List<Int>): List<Movie>

    fun observePopular(ids: List<Int>): Flow<List<Movie>>

    suspend fun searchMovies(query: String): List<Movie>

    suspend fun getTrailer(movieId: Int): String?

    suspend fun getMovie(id: Int): Movie?

    suspend fun getMoviesById(ids: List<Int>): List<Movie>

    suspend fun getRecentSix(ids: List<Int>): List<Movie>
}

class MoviesRepositoryImpl @Inject constructor(
    private val remoteSource: MoviesRemoteSource,
    private val localSource: MoviesLocalSource
) : MoviesRepository {
    override suspend fun fetchPopularMovies(): NetworkResult<MoviesResponse> {
        return fetchMoviesByCategory(MovieDBService.Categories.Popular)
    }

    override suspend fun fetchNowPlayingMovies(): NetworkResult<MoviesResponse> {
        return fetchMoviesByCategory(MovieDBService.Categories.NowPlaying)
    }

    override suspend fun fetchTopRatedMovies(): NetworkResult<MoviesResponse> {
        return fetchMoviesByCategory(MovieDBService.Categories.TopRated)
    }

    override suspend fun fetchUpcomingMovies(): NetworkResult<MoviesResponse> {
        return fetchMoviesByCategory(MovieDBService.Categories.Upcoming)
    }

    private suspend fun fetchMoviesByCategory(
        category: MovieDBService.Categories
    ): NetworkResult<MoviesResponse> {
        val result = remoteSource.getByCategory(category)

        when(result) {
            is NetworkResult.Error -> Unit //Do nothing, if network error don't delete from local
            is NetworkResult.Success -> {
                localSource.insertMovies(result.data.results)
            }
        }

        return result
    }

    override suspend fun getLocalPopular(ids: List<Int>) = localSource.getPopular(ids)

    override fun observePopular(ids: List<Int>) = localSource.observePopular(ids)

    override suspend fun searchMovies(query: String): List<Movie> {
        val response = remoteSource.search(query)
        //always update local for future offline situations, but only return exclusive from
        //local if there was an error, LIKE searches can be widely different across systems
        return when (response) {
            is NetworkResult.Error -> {
                localSource.searchMovies(query)
            }
            is NetworkResult.Success -> {
                val serverMovies = response.data.results
                localSource.insertMovies(serverMovies)
                serverMovies
            }
        }
    }

    override suspend fun getTrailer(movieId: Int): String? {
        return when(val response = remoteSource.trailers(movieId)) {
            is NetworkResult.Error -> null
            is NetworkResult.Success -> response.data.trailer()
        }
    }

    override suspend fun getMovie(id: Int) = localSource.getMovie(id)

    override suspend fun getMoviesById(ids: List<Int>) = localSource.getMoviesById(ids)

    override suspend fun getRecentSix(ids: List<Int>) = localSource.getRecentSix(ids)
}