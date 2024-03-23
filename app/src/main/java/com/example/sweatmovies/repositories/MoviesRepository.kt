package com.example.sweatmovies.repositories

import com.example.sweatmovies.models.MoviesResponse
import com.example.sweatmovies.sources.NetworkResult
import com.example.sweatmovies.sources.movies.MoviesLocalSource
import com.example.sweatmovies.sources.movies.MoviesRemoteSource
import javax.inject.Inject

interface MoviesRepository {
    suspend fun getPopularMovies(): NetworkResult<MoviesResponse>
}

class MoviesRepositoryImpl @Inject constructor(
    private val remoteSource: MoviesRemoteSource,
    private val localSource: MoviesLocalSource
) : MoviesRepository {
    override suspend fun getPopularMovies(): NetworkResult<MoviesResponse> {
        val result = remoteSource.getPopular()

        when(result) {
            is NetworkResult.Error -> Unit //Do nothing, if network error don't delete from local
            is NetworkResult.Success -> {
                localSource.insertMovies(result.data.results)
            }
        }

        return result
    }

}