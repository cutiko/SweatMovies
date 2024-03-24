package com.example.sweatmovies.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey override  val id: Int = 0,
    override val adult: Boolean = false,
    override val backdropPath: String = "",
    val genreIds: List<Int> = emptyList(),
    override val originalLanguage: String = "",
    val originalTitle: String = "",
    override val overview: String = "",
    val popularity: Double = 0.0,
    override val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val lastUpdate: Long? = 0L
) : MovieType