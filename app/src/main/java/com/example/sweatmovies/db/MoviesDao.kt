package com.example.sweatmovies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sweatmovies.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movie WHERE id IN (:ids) ORDER BY lastUpdate DESC LIMIT 20")
    fun getRecentPopular(ids: List<Int>): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)
}
