package com.example.sweatmovies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.Movie.Origin
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movie WHERE origin = :origin")
    fun getAllPopular(origin: Origin = Origin.Popular): Flow<List<Movie>>

    /**
     * Use the methods annotated with transactions to guarantee app functionality
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    /**
     * We are not handling pagination so we can delete all the old movies
     */
    @Query("DELETE FROM Movie WHERE origin = :origin AND id NOT IN (:ids)")
    suspend fun deleteNotIncludedIds(ids: List<Int>, origin: Origin)

    /**
     * Delete all the old popular movies and insert/update the fresh batch
     */
    @Transaction
    suspend fun insertPopular(movies: List<Movie>) {
        val ids = movies.map { it.id }
        deleteNotIncludedIds(ids, Origin.Popular)
        insert(movies)
    }

}