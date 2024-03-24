package com.example.sweatmovies.sources.movies

import com.example.sweatmovies.db.MoviesDao
import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.Movie
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
    fun `test observePopular calls the DAO`() {
        val ids = listOf(1, 2, 3)
        val idsSlot = slot<List<Int>>()
        every { moviesDao.observeRecentPopular(capture(idsSlot)) } returns MutableStateFlow(emptyList())

        localSource.observePopular(ids)

        assertEquals(ids, idsSlot.captured)
        verify(exactly = 1) { moviesDao.observeRecentPopular(ids) }
        confirmVerified(moviesDao)
    }

    @Test
    fun `test getPopular calls the DAO`() = runTest {
        val ids = listOf(1, 2, 3)
        val movies = ids.map { Movie(id = it) }
        val idsSlot = slot<List<Int>>()
        coEvery { moviesDao.getRecentPopular(capture(idsSlot)) } returns movies

        val obtained = localSource.getPopular(ids)

        assertEquals(ids, idsSlot.captured)
        coVerify(exactly = 1) { moviesDao.getRecentPopular(ids) }
        confirmVerified(moviesDao)
        assertEquals(movies, obtained)
    }

    @Test
    fun `test insertPopular calls the DAO`() = runTest {
        val movies = listOf(
            Movie(id = 1),
            Movie(id = 2)
        )
        coEvery { moviesDao.insert(any()) } just runs

        localSource.insertMovies(movies)

        coVerify(exactly = 1) { moviesDao.insert(any()) }
        confirmVerified(moviesDao)
    }

    @Test
    fun `test searchMovies calls the DAO`() = runTest {
        coEvery { moviesDao.likeSearch(any()) } returns emptyList()

        localSource.searchMovies("title")

        coVerify(exactly = 1) { moviesDao.likeSearch(any()) }
        confirmVerified(moviesDao)
    }

}