package com.example.sweatmovies.ui.search

import com.example.sweatmovies.ViewModelTest
import com.example.sweatmovies.models.MovieOverview
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.update
import com.example.sweatmovies.ui.search.usecases.SearchMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class SearchMovieViewModelTest : ViewModelTest {

    private lateinit var useCase: SearchMoviesUseCase
    private lateinit var viewModel: SearchMovieViewModel

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = SearchMovieViewModel(useCase)
    }

    @Test
    fun `test search updates the resultItems`() = runUnconfinedTest {
        val expectedTerm = "madame web"
        val termSlot = slot<String>()
        val overviews = listOf(MovieOverview(id = 1), MovieOverview(id = 2), MovieOverview(id = 3))
        coEvery { useCase.byTerm(capture(termSlot)) } returns overviews

        val original = viewModel.resultItems.value
        viewModel.search(expectedTerm)
        val updated = viewModel.resultItems.value

        assertEquals(expectedTerm, termSlot.captured)
        coVerify(exactly = 1) { useCase.byTerm(expectedTerm) }
        confirmVerified(useCase)
        val default = SearchResultItem.default
        assertEquals(original, default)
        val expected = default.update(overviews)
        assertEquals(expected, updated)
    }

}