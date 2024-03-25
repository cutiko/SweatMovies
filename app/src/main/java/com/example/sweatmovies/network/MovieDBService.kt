package com.example.sweatmovies.network

import com.example.sweatmovies.models.MoviesResponse
import com.example.sweatmovies.models.TrailersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBService {

    @GET("movie/{category}")
    suspend fun byCategory(
        @Path("category") category: String,
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

    @GET("movie/{movieId}/videos")
    suspend fun trailers(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "en-US",
    ): Response<TrailersResponse>

    enum class Categories(val path: String) {
        Popular("popular"),
        NowPlaying("now_playing"),
        TopRated("top_rated"),
        Upcoming("upcoming")
    }

}