package com.example.sweatmovies.models

import androidx.room.Ignore

interface MovieType {

    val adult: Boolean
    val backdropPath: String
    val id: Int
    val originalLanguage: String
    val overview: String
    val posterPath: String
    @Ignore
    fun poster() = "https://image.tmdb.org/t/p/w1280/$backdropPath"
}