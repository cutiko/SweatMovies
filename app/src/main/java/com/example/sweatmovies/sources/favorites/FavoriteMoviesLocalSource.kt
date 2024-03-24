package com.example.sweatmovies.sources.favorites

import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FavoriteMoviesLocalSource {

    fun observeFavorites(): Flow<List<Int>>

    suspend fun addFavorite(id: Int)

    suspend fun deleteFavorite(id: Int)

    fun observeFavorite(id: Int): Flow<Boolean>

}

class FavoriteMoviesPersistenceSource @Inject constructor(
    private val moviesDataBase: MoviesDataBase
) : FavoriteMoviesLocalSource {
    override fun observeFavorites() = moviesDataBase.favoritesDao().observeFavorites()

    override suspend fun addFavorite(id: Int) {
        val favorite = FavoriteMovie(id)
        moviesDataBase.favoritesDao().insert(favorite)
    }

    override suspend fun deleteFavorite(id: Int) = moviesDataBase.favoritesDao().delete(id)

    override fun observeFavorite(id: Int) = moviesDataBase.favoritesDao().observeIsFavorite(id).map {
        it == true
    }

}