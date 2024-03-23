package com.example.sweatmovies.sources.categories

import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.MovieCategories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesCategoriesLocalSource {
    fun observeCategories(): Flow<MovieCategories?>
    suspend fun updatePopular(ids: List<Int>)

    suspend fun getCategories(): MovieCategories
}

class MoviesCategoriesPersistenceSource @Inject constructor(
    private val moviesDataBase: MoviesDataBase
) : MoviesCategoriesLocalSource {
    override fun observeCategories() = moviesDataBase.categoriesDao().observeMovieCategories()
    override suspend fun updatePopular(ids: List<Int>) {
        val categories = moviesDataBase.categoriesDao().safeGetMovieCategories()
        val updatedCategories = categories.copy(popular = ids)
        moviesDataBase.categoriesDao().insert(updatedCategories)
    }
    override suspend fun getCategories(): MovieCategories {
        return moviesDataBase.categoriesDao().safeGetMovieCategories()
    }

}