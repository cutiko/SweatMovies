package com.example.sweatmovies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MovieCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCategoriesDao {
    @Query("SELECT * FROM MovieCategories WHERE uniqueId = 1")
    fun observeMovieCategories(): Flow<MovieCategories?>

    @Query("SELECT * FROM MovieCategories WHERE uniqueId = 1")
    suspend fun getMovieCategories(): MovieCategories?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieCategories: MovieCategories)

    @Transaction
    suspend fun safeGetMovieCategories(): MovieCategories {
        val currentCategories = getMovieCategories() ?: MovieCategories()
        insert(currentCategories)
        return currentCategories
    }

}
