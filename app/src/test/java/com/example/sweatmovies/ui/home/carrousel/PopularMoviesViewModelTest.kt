package com.example.sweatmovies.ui.home.carrousel

import com.example.sweatmovies.ViewModelTest
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem.Companion.update
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase.Result
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test


class PopularMoviesViewModelTest : ViewModelTest {

    private lateinit var useCase: GetMoviesByCategoryUseCase
    private lateinit var viewModel: PopularMoviesViewModel

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = PopularMoviesViewModel(useCase)
    }

    @Test
    fun `test startObserving updates the carrouselItems`() = runUnconfinedTest {
        val result = Result.Success(emptyList())
        every { useCase.observePopularMovies() } returns MutableStateFlow(result)

        val originalItems = viewModel.carrouselItems.value
        viewModel.startObserving()
        val updatedItems = viewModel.carrouselItems.value

        val default = PopularCarrouselItem.default
        assertEquals(default, originalItems)
        val expected = default.update(result)
        assertEquals(expected, updatedItems)
    }

}