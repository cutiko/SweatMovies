package com.example.sweatmovies.models

data class MoviesResponse<MovieType>(
    val page: Int = 0,
    val totalPages: Int = 0,
    val totalResults: Int = 0,
    val results: List<MovieType> = emptyList()
)
