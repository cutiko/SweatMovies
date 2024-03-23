package com.example.sweatmovies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MovieCategories

@Database(entities = [Movie::class, MovieCategories::class], version = 1)
@TypeConverters(MoviesConverters::class)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun moviesDao() : MoviesDao
    abstract fun categoriesDao(): MovieCategoriesDao
}