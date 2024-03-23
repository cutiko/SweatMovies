package com.example.sweatmovies.repositories

import com.example.sweatmovies.models.MovieCategories
import com.example.sweatmovies.sources.categories.MoviesCategoriesLocalSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieCategoriesRepository {
    fun observeCategories(): Flow<MovieCategories?>
    suspend fun updatePopular(ids: List<Int>)

    suspend fun getCategories(): MovieCategories
}

class MovieCategoriesRepositoryImpl @Inject constructor(
    private val categoriesLocalSource: MoviesCategoriesLocalSource
) : MovieCategoriesRepository {
    override fun observeCategories() = categoriesLocalSource.observeCategories()

    override suspend fun updatePopular(ids: List<Int>) = categoriesLocalSource.updatePopular(ids)

    override suspend fun getCategories() = categoriesLocalSource.getCategories()

}