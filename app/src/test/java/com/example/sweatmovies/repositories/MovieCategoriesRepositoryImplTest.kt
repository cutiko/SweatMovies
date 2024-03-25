package com.example.sweatmovies.repositories

import com.example.sweatmovies.models.MovieCategories
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.sources.categories.MoviesCategoriesLocalSource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieCategoriesRepositoryImplTest {

    private lateinit var categoriesLocalSource: MoviesCategoriesLocalSource
    private lateinit var repository: MovieCategoriesRepositoryImpl

    @Before
    fun setup() {
        categoriesLocalSource = mockk()
        repository = MovieCategoriesRepositoryImpl(categoriesLocalSource)
    }

    @Test
    fun `test observeCategories calls the source`() {
        every { categoriesLocalSource.observeCategories() } returns MutableStateFlow(null)

        repository.observeCategories()

        verify(exactly = 1) { categoriesLocalSource.observeCategories() }
        confirmVerified(categoriesLocalSource)
    }

    @Test
    fun `test update calls the source`() = runTest {
        val ids = listOf(1, 2, 3)
        val category = MovieDBService.Categories.Popular
        coEvery { repository.update(ids, category) } just Runs

        repository.update(ids, category)

        coVerify(exactly = 1) { categoriesLocalSource.updatePopular(ids) }
        confirmVerified(categoriesLocalSource)
    }

    @Test
    fun `test getCategories calls the source`() = runTest {
        val expectedCategories = MovieCategories(popular = listOf(1, 2, 3))
        coEvery { repository.getCategories() } returns expectedCategories

        val obtained = repository.getCategories()

        coVerify(exactly = 1) { categoriesLocalSource.getCategories() }
        confirmVerified(categoriesLocalSource)
        assertEquals(expectedCategories, obtained)
    }

}