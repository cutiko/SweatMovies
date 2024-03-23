package com.example.sweatmovies.ui.home.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MovieCategories
import com.example.sweatmovies.repositories.MovieCategoriesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
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
    fun `test observePopularMovies updates from loading to success`() = runTest {
        coEvery { categoriesRepository.getCategories() } returns MovieCategories()
        coEvery { moviesRepository.getLocalPopular(any()) } returns emptyList()
        coEvery { moviesRepository.fetchPopularMovies() } returns NetworkResult.Error()

        val emissions = useCase.observePopularMovies().take(2).toList()

        assertEquals(Result.Loading, emissions.first())
        assertTrue("Received ${emissions.last()} but was waiting success", emissions.last() is Result.Success)
    }

    @Test
    fun `test observePopularMovies updates from success to success`() = runTest {
        val movies = listOf(Movie(), Movie())
        coEvery { categoriesRepository.getCategories() } returns MovieCategories()
        coEvery { moviesRepository.getLocalPopular(any()) } returns movies
        coEvery { moviesRepository.fetchPopularMovies() } returns NetworkResult.Error()

        val emissions = useCase.observePopularMovies().take(2).toList()

        //Since both emissions are success we have to make sure it emitted twice
        assertEquals(2, emissions.size)
        assertTrue(emissions.first() is Result.Success)
        assertTrue(emissions.last() is Result.Success)
    }

}