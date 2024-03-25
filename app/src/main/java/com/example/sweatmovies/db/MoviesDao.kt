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
    fun observeRecentPopular(ids: List<Int>): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id IN (:ids) ORDER BY lastUpdate DESC LIMIT 20")
    suspend fun getRecentTwenty(ids: List<Int>): List<Movie>

    @Query("SELECT * FROM Movie WHERE id IN (:ids) ORDER BY lastUpdate DESC LIMIT 6")
    suspend fun getRecentSix(ids: List<Int>): List<Movie>

    @Query("SELECT * FROM Movie WHERE id IN (:ids) ORDER BY title")
    suspend fun getMoviesById(ids: List<Int>): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Query("SELECT * FROM Movie WHERE title LIKE '%' || :title || '%'")
    suspend fun likeSearch(title: String): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :id")
    suspend fun byId(id: Int): Movie?
}
