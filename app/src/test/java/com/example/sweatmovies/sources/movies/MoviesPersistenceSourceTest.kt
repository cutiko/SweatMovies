package com.example.sweatmovies.sources.movies

import com.example.sweatmovies.db.MoviesDao
import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.Movie.Origin
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesPersistenceSourceTest {

    private lateinit var moviesDataBase: MoviesDataBase
    private lateinit var moviesDao: MoviesDao
    private lateinit var localSource: MoviesPersistenceSource
    @Before
    fun setup() {
        moviesDataBase = mockk()
        moviesDao = mockk()
        every { moviesDataBase.moviesDao() } returns moviesDao
        localSource = MoviesPersistenceSource(moviesDataBase)
    }

    @Test
    fun `test getAllPopular calls the DAO`() {
        every { moviesDao.getAllPopular() } returns MutableStateFlow(emptyList())

        localSource.getAllPopular()

        verify(exactly = 1) { moviesDao.getAllPopular() }
        confirmVerified(moviesDao)
    }

    @Test
    fun `test insertPopular calls the DAO`() = runTest {
        val movies = listOf(
            Movie(id = 1),
            Movie(id = 2)
        )
        val moviesSlot = slot<List<Movie>>()
        coEvery { moviesDao.insertPopular(
            capture(moviesSlot)
        ) } just runs

        localSource.insertPopular(movies)

        val expectedArgument = movies.map { it.copy(origin = Origin.Popular) }
        assertEquals(expectedArgument, moviesSlot.captured)
        coVerify(exactly = 1) { moviesDao.insertPopular(expectedArgument) }
        confirmVerified(moviesDao)
    }

}