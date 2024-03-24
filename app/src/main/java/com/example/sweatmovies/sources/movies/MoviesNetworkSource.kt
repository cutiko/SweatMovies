package com.example.sweatmovies.sources.movies

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MovieOverview
import com.example.sweatmovies.models.MoviesResponse
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.sources.BaseNetworkSource
import com.example.sweatmovies.sources.NetworkResult
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

interface MoviesRemoteSource : BaseNetworkSource {
    suspend fun getPopular() : NetworkResult<MoviesResponse<Movie>>

    suspend fun search(query: String): NetworkResult<MoviesResponse<MovieOverview>>
}

class MoviesNetworkSource @Inject constructor(
    private val retrofit: Retrofit
) : MoviesRemoteSource {

    private val moviesServices by lazy {
        retrofit.create(MovieDBService::class.java)
    }
    override suspend fun getPopular(): NetworkResult<MoviesResponse<Movie>> {
        return request {
            moviesServices.getPopular()
        }
    }

    override suspend fun search(query: String): NetworkResult<MoviesResponse<MovieOverview>> {
        return request { moviesServices.search(query) }
    }

}

