package com.example.sweatmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieCategories(
    val nowPlaying: List<Int> = emptyList(),
    val popular: List<Int> = emptyList(),
    val upComing: List<Int> = emptyList(),
    val topRated: List<Int> = emptyList()
) {
    //Unique primary key, only one is needed, setter is a work around to make it ROOM compatible
    @PrimaryKey var uniqueId: Int = 1
        set(value) {
            field = 1
        }
}