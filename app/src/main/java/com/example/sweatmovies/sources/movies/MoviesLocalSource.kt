package com.example.sweatmovies.sources.movies

import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.Movie.Origin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesLocalSource {
    fun getAllPopular(): Flow<List<Movie>>

    suspend fun insertPopular(movies: List<Movie>)
}

class MoviesPersistenceSource @Inject constructor(
    private val dataBase: MoviesDataBase
) : MoviesLocalSource {

    override fun getAllPopular(): Flow<List<Movie>> {
        return dataBase.moviesDao().getAllPopular()
    }

    override suspend fun insertPopular(movies: List<Movie>) {
        val populars = movies.map { it.copy(origin = Origin.Popular) }
        dataBase.moviesDao().insertPopular(populars)
    }

}