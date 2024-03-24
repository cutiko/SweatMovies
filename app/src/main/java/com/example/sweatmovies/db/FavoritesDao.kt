package com.example.sweatmovies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sweatmovies.models.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT id FROM FavoriteMovie")
    fun observeFavorites(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteMovie)
    @Query("DELETE FROM FavoriteMovie where id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT id FROM FavoriteMovie WHERE id = :id")
    fun observeIsFavorite(id: Int): Flow<Boolean?>

}