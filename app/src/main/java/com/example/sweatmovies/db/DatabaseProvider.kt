package com.example.sweatmovies.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun db(applicationContext: Context): MoviesDataBase = Room.databaseBuilder(
        applicationContext,
        MoviesDataBase::class.java, "sweat_movies_db"
    ).build()

}

