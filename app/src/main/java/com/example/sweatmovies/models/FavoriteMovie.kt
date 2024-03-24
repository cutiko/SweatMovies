package com.example.sweatmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovie(
    @PrimaryKey val id: Int = 0
)
