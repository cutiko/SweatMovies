package com.example.sweatmovies.network

import com.example.sweatmovies.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBService {

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MoviesResponse>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("include_adult") adultContent: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MoviesResponse>
}