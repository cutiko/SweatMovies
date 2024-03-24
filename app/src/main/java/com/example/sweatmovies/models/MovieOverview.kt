package com.example.sweatmovies.models

data class MovieOverview(
    override val adult: Boolean = false,
    override val backdropPath: String = "",
    override val id: Int = 0,
    val name: String = "",
    override val originalLanguage: String = "",
    val originalName: String = "",
    override val overview: String = "",
    override val posterPath: String = ""
) : MovieType