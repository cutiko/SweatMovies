package com.example.sweatmovies.ui.details

import com.example.sweatmovies.repositories.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieDetailsUseCaseTest {

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var useCase: MovieDetailsUseCase

    @Before
    fun setup() {
        moviesRepository = mockk()
        useCase = MovieDetailsUseCase(moviesRepository)
    }

    @Test
    fun `test getMovie call the repository`() = runTest {
        coEvery { moviesRepository.getMovie(any()) } returns null

        useCase.getMovie(123)

        coVerify(exactly = 1) { moviesRepository.getMovie(any()) }
        confirmVerified(moviesRepository)
    }

    @Test
    fun `test getTrailer call the repository`() = runTest {
        coEvery { moviesRepository.getTrailer(any()) } returns null

        useCase.getTrailer(123)

        coVerify(exactly = 1) { moviesRepository.getTrailer(any()) }
        confirmVerified(moviesRepository)
    }

}