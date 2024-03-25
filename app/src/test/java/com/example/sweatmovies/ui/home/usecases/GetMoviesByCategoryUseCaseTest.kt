package com.example.sweatmovies.ui.home.usecases

import com.example.sweatmovies.models.MovieCategories
import com.example.sweatmovies.repositories.MovieCategoriesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMoviesByCategoryUseCaseTest {

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var categoriesRepository: MovieCategoriesRepository
    private lateinit var useCase: GetMoviesByCategoryUseCase

    @Before
    fun setup() {
        moviesRepository = mockk()
        categoriesRepository = mockk()
        useCase = GetMoviesByCategoryUseCase(moviesRepository, categoriesRepository)
    }

    @Test
    fun `test getPopularMovies call the categories repository when the network fail`() = runTest {
        coEvery { moviesRepository.fetchPopularMovies() } returns NetworkResult.Error()
        coEvery { categoriesRepository.getCategories() } returns MovieCategories()
        coEvery { moviesRepository.getLocalPopular(any()) } returns emptyList()


        useCase.getPopularMovies()

        coVerify(exactly = 1) { categoriesRepository.getCategories() }
    }

}