package com.example.sweatmovies.sources.movies

import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesLocalSource {
    fun observePopular(ids: List<Int>): Flow<List<Movie>>

    suspend fun getPopular(ids: List<Int>): List<Movie>

    suspend fun insertMovies(movies: List<Movie>)

    suspend fun searchMovies(term: String): List<Movie>

    suspend fun getMovie(id: Int): Movie?

    suspend fun getMoviesById(ids: List<Int>): List<Movie>

    suspend fun getRecentSix(ids: List<Int>): List<Movie>
}

class MoviesPersistenceSource @Inject constructor(
    private val dataBase: MoviesDataBase
) : MoviesLocalSource {

    override fun observePopular(ids: List<Int>): Flow<List<Movie>> {
        return dataBase.moviesDao().observeRecentPopular(ids)
    }

    override suspend fun getPopular(ids: List<Int>): List<Movie> {
        return dataBase.moviesDao().getRecentTwenty(ids)
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        val populars = movies.map { it.copy(lastUpdate = System.currentTimeMillis()) }
        dataBase.moviesDao().insert(populars)
    }

    override suspend fun searchMovies(term: String) = dataBase.moviesDao().likeSearch(term)

    override suspend fun getMovie(id: Int) = dataBase.moviesDao().byId(id)

    override suspend fun getMoviesById(ids: List<Int>) = dataBase.moviesDao().getMoviesById(ids)

    override suspend fun getRecentSix(ids: List<Int>) = dataBase.moviesDao().getRecentSix(ids)
}