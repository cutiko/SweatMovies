package com.example.sweatmovies.repositories

import com.example.sweatmovies.models.MovieCategories
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.network.MovieDBService.Categories.*
import com.example.sweatmovies.sources.categories.MoviesCategoriesLocalSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieCategoriesRepository {
    fun observeCategories(): Flow<MovieCategories?>
    suspend fun update(ids: List<Int>, category: MovieDBService.Categories)

    suspend fun getCategories(): MovieCategories
}

class MovieCategoriesRepositoryImpl @Inject constructor(
    private val categoriesLocalSource: MoviesCategoriesLocalSource
) : MovieCategoriesRepository {
    override fun observeCategories() = categoriesLocalSource.observeCategories()

    override suspend fun update(ids: List<Int>, category: MovieDBService.Categories) {
        when(category) {
            Popular -> categoriesLocalSource.updatePopular(ids)
            NowPlaying -> categoriesLocalSource.updateNowPlaying(ids)
            TopRated -> categoriesLocalSource.updateTopRated(ids)
            Upcoming -> categoriesLocalSource.updateUpcoming(ids)
        }
    }

    override suspend fun getCategories() = categoriesLocalSource.getCategories()

}