package com.example.sweatmovies.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val id: Int = 0,
    val adult: Boolean = false,
    val backdropPath: String = "",
    val genreIds: List<Int> = emptyList(),
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val lastUpdate: Long? = 0L
) {
    @Ignore
    val photo = "$PHOTO_BASE_URL$backdropPath"

    companion object {
        private const val PHOTO_BASE_URL = "https://image.tmdb.org/t/p/w1280/"
    }
}