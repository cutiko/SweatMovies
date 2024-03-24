package com.example.sweatmovies.ui.search.usecases

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MovieOverview
import com.example.sweatmovies.models.MoviesResponse
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.sources.NetworkResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchMoviesUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Before
    fun setup() {
        repository = mockk()
        searchMoviesUseCase = SearchMoviesUseCase(repository)
    }

    @Test
    fun `test byTerm does not call the repository with empty term`() = runTest {
        val expectedQuery = "   "

        val obtained = searchMoviesUseCase.byTerm(expectedQuery)

        coVerify(exactly = 0) { repository.searchMovies(any()) }
        confirmVerified(repository)
        assertEquals(emptyList<MovieOverview>(), obtained)
    }
    @Test
    fun `test byTerm calls the repository`() = runTest {
        val expectedQuery = "dune"
        val querySlot = slot<String>()
        val overviews = listOf(MovieOverview(), MovieOverview(), MovieOverview())
        val response = MoviesResponse(results = overviews)
        val result = NetworkResult.Success(response)
        coEvery { repository.searchMovies(capture(querySlot)) } returns result

        val obtained = searchMoviesUseCase.byTerm(expectedQuery)

        assertEquals(expectedQuery, querySlot.captured)
        coVerify(exactly = 1) { repository.searchMovies(expectedQuery) }
        confirmVerified(repository)
        assertEquals(overviews, obtained)
    }

    @Test
    fun `test byTerm uses the softCache`() = runTest {
        val expectedQuery = "dune"
        val overviews = listOf(MovieOverview(), MovieOverview(), MovieOverview())
        val response = MoviesResponse(results = overviews)
        val result = NetworkResult.Success(response)
        coEvery { repository.searchMovies(any()) } returns result

        searchMoviesUseCase.byTerm(expectedQuery)
        searchMoviesUseCase.byTerm(expectedQuery)

        //if we call the method twice then the second time should not call the repo because cache
        coVerify(exactly = 1) { repository.searchMovies(expectedQuery) }
        confirmVerified(repository)
    }

}