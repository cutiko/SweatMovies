package com.example.sweatmovies.sources.categories

import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.MovieCategories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesCategoriesLocalSource {
    fun observeCategories(): Flow<MovieCategories?>
    suspend fun updatePopular(ids: List<Int>)

    suspend fun updateNowPlaying(ids: List<Int>)

    suspend fun updateUpcoming(ids: List<Int>)

    suspend fun updateTopRated(ids: List<Int>)

    suspend fun getCategories(): MovieCategories
}

class MoviesCategoriesPersistenceSource @Inject constructor(
    private val moviesDataBase: MoviesDataBase
) : MoviesCategoriesLocalSource {
    override fun observeCategories() = moviesDataBase.categoriesDao().observeMovieCategories()
    override suspend fun updatePopular(ids: List<Int>) {
        val categories = getSafeCategories()
        val updatedCategories = categories.copy(popular = ids)
        moviesDataBase.categoriesDao().insert(updatedCategories)
    }
    override suspend fun updateNowPlaying(ids: List<Int>) {
        val categories = getSafeCategories()
        val updatedCategories = categories.copy(nowPlaying = ids)
        moviesDataBase.categoriesDao().insert(updatedCategories)
    }

    override suspend fun updateUpcoming(ids: List<Int>) {
        val categories = getSafeCategories()
        val updatedCategories = categories.copy(upComing = ids)
        moviesDataBase.categoriesDao().insert(updatedCategories)
    }

    override suspend fun updateTopRated(ids: List<Int>) {
        val categories = getSafeCategories()
        val updatedCategories = categories.copy(topRated = ids)
        moviesDataBase.categoriesDao().insert(updatedCategories)
    }
    private suspend fun getSafeCategories() = moviesDataBase.categoriesDao().safeGetMovieCategories()

    override suspend fun getCategories(): MovieCategories {
        return moviesDataBase.categoriesDao().safeGetMovieCategories()
    }

}