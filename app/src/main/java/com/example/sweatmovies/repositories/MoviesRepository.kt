package com.example.sweatmovies.repositories

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MoviesResponse
import com.example.sweatmovies.sources.NetworkResult
import com.example.sweatmovies.sources.movies.MoviesLocalSource
import com.example.sweatmovies.sources.movies.MoviesRemoteSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesRepository {
    suspend fun fetchPopularMovies(): NetworkResult<MoviesResponse<Movie>>

    suspend fun getLocalPopular(ids: List<Int>): List<Movie>

    fun observePopular(ids: List<Int>): Flow<List<Movie>>
}

class MoviesRepositoryImpl @Inject constructor(
    private val remoteSource: MoviesRemoteSource,
    private val localSource: MoviesLocalSource
) : MoviesRepository {
    override suspend fun fetchPopularMovies(): NetworkResult<MoviesResponse<Movie>> {
        val result = remoteSource.getPopular()

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

}