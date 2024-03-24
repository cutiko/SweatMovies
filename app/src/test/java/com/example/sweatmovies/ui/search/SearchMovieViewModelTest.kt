package com.example.sweatmovies.ui.search

import com.example.sweatmovies.ViewModelTest
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.search.uimodels.SearchResultsScreenState
import com.example.sweatmovies.ui.search.uimodels.SearchResultsScreenState.Companion.updateResults
import com.example.sweatmovies.ui.search.uimodels.SearchResultsScreenState.Companion.updateUserInput
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
    fun `test search calls the use case`() = runUnconfinedTest {
        val expectedTerm = "madame web"
        val termSlot = slot<String>()
        coEvery { useCase.byTerm(capture(termSlot)) } returns emptyList()

        viewModel.search(expectedTerm)

        assertEquals(expectedTerm, termSlot.captured)
        coVerify(exactly = 1) { useCase.byTerm(expectedTerm) }
        confirmVerified(useCase)
    }

    @Test
    fun `test search updates the screenState`() = runUnconfinedTest {
        val term = "titanic"
        val movies = listOf(Movie(id = 1), Movie(id = 2), Movie(id = 3))
        coEvery { useCase.byTerm(any()) } returns movies

        val original = viewModel.screenState.value
        viewModel.search(term)
        val updated = viewModel.screenState.value


        val default = SearchResultsScreenState.default
        assertEquals(original, default)
        val expected = default.updateUserInput(term).updateResults(movies)
        assertEquals(expected, updated)
    }

}