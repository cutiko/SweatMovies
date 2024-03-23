package com.example.sweatmovies.sources.categories

import com.example.sweatmovies.db.MovieCategoriesDao
import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.MovieCategories
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesCategoriesPersistenceSourceTest {

    private lateinit var moviesDataBase: MoviesDataBase
    private lateinit var categoriesDao: MovieCategoriesDao
    private lateinit var localSource: MoviesCategoriesPersistenceSource

    @Before
    fun setup() {
        moviesDataBase = mockk()
        categoriesDao = mockk()
        every { moviesDataBase.categoriesDao() } returns categoriesDao
        localSource = MoviesCategoriesPersistenceSource(moviesDataBase)
    }

    @Test
    fun `test observeCategories calls the DAO`() {
        every { categoriesDao.observeMovieCategories() } returns MutableStateFlow(null)

        localSource.observeCategories()

        verify(exactly = 1) { categoriesDao.observeMovieCategories() }
        confirmVerified(categoriesDao)
    }

    @Test
    fun `test updatePopular calls the DAO`() = runTest {
        val originalCategories = MovieCategories(
            popular = listOf(1, 2, 3)
        )
        val ids = listOf(5, 6, 7)
        coEvery { categoriesDao.safeGetMovieCategories() } returns originalCategories
        coEvery { categoriesDao.insert(any()) } just Runs

        localSource.updatePopular(ids)

        coVerify(exactly = 1) { categoriesDao.safeGetMovieCategories() }
        val updatedCategories = originalCategories.copy(popular = ids)
        coVerify(exactly = 1) { categoriesDao.insert(updatedCategories) }
        confirmVerified(categoriesDao)
    }

    @Test
    fun `test getCategories calls the DAO`() = runTest {
        val originalCategories = MovieCategories(
            popular = listOf(1, 2, 3)
        )
        coEvery { categoriesDao.safeGetMovieCategories() } returns originalCategories

        val obtained = localSource.getCategories()

        coVerify(exactly = 1) { categoriesDao.safeGetMovieCategories() }
        confirmVerified(categoriesDao)
        assertEquals(originalCategories, obtained)
    }

}