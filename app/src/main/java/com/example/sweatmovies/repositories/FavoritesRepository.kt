package com.example.sweatmovies.repositories

import com.example.sweatmovies.sources.favorites.FavoriteMoviesLocalSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoritesRepository {

    fun observeFavorites(): Flow<List<Int>>

    suspend fun update(favorite: Boolean, id: Int)
    fun observeFavorite(id: Int): Flow<Boolean>

}

class FavoritesRepositoryImpl @Inject constructor(
    private val localSource: FavoriteMoviesLocalSource
) : FavoritesRepository {
    override fun observeFavorites() = localSource.observeFavorites()

    override suspend fun update(favorite: Boolean, id: Int) {
        if (favorite) {
            localSource.addFavorite(id)
        } else {
            localSource.deleteFavorite(id)
        }
    }
    override fun observeFavorite(id: Int) = localSource.observeFavorite(id)

}