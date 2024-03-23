package com.example.sweatmovies.sources.movies

import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesLocalSource {
    fun getAllPopular(ids: List<Int>): Flow<List<Movie>>

    suspend fun insertMovies(movies: List<Movie>)
}

class MoviesPersistenceSource @Inject constructor(
    private val dataBase: MoviesDataBase
) : MoviesLocalSource {

    override fun getAllPopular(ids: List<Int>): Flow<List<Movie>> {
        return dataBase.moviesDao().getRecentPopular(ids)
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        val populars = movies.map { it.copy(lastUpdate = System.currentTimeMillis()) }
        dataBase.moviesDao().insert(populars)
    }

}