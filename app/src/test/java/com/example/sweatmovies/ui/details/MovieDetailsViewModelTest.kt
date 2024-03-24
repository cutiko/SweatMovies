package com.example.sweatmovies.ui.details

import com.example.sweatmovies.ViewModelTest
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.details.uimodels.DetailsScreenState
import com.example.sweatmovies.ui.details.uimodels.DetailsScreenState.Companion.make
import com.example.sweatmovies.ui.details.usecases.MovieDetailsUseCase
import com.example.sweatmovies.ui.details.usecases.MovieDetailsUseCase.MovieAndFavorite
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest : ViewModelTest {

    private lateinit var useCase: MovieDetailsUseCase
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = MovieDetailsViewModel(useCase)
    }

    @Test
    fun `test observeMovie updates the state`() = runUnconfinedTest {
        val movie = Movie()
        val movieAndFavorite = MovieAndFavorite(movie, false)
        coEvery { useCase.observeMovie(any()) } returns MutableStateFlow(movieAndFavorite)

        val original = viewModel.state.value
        viewModel.observeMovie(123)
        val update = viewModel.state.value

        val default = DetailsScreenState.default
        assertEquals(default, original)
        val expected = default.make(movie, false)
        assertEquals(expected, update)
    }


}