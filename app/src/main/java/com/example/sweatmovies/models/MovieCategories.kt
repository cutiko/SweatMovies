package com.example.sweatmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is a control table. We have to show different categories, so my first approach was to
 * add an attribute to the [Movie], depending on the request then we can set that attribute.
 * The problem is: what happen if a movie is in more than one category? So that changes
 * the attribute to a collection of categories, and then everytime we have to update the [Movie]
 * we have to carefully not override that category attribute, and then what happens if the movie
 * is no longer in a category, everytime we request from a category we have to update every movie
 * that is no longer in the category. Also, what happens when the user search for a movie?
 * Considering such a complications: this is a control table for that. That way we can get
 * the movies from a category from the DB for offline.
 * Usually this should be documented else where but considering the circumstance I rather just
 * write it here.
 */
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