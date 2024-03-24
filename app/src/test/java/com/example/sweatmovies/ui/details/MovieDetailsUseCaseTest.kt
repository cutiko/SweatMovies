package com.example.sweatmovies.ui.details

import com.example.sweatmovies.repositories.FavoritesRepository
import com.example.sweatmovies.repositories.MoviesRepository
import com.example.sweatmovies.ui.details.usecases.MovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieDetailsUseCaseTest {

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var useCase: MovieDetailsUseCase

    @Before
    fun setup() {
        moviesRepository = mockk()
        favoritesRepository = mockk()
        useCase = MovieDetailsUseCase(moviesRepository, favoritesRepository)
    }

    @Test
    fun `test observeMovie call the repositories`() = runTest {
        coEvery { moviesRepository.getMovie(any()) } returns null
        every { favoritesRepository.observeFavorite(any()) } returns MutableStateFlow(false)

        //we have to observe it otherwise the map never triggers getting the movie from the DB
        useCase.observeMovie(123).take(1).toList()

        coVerify(exactly = 1) { moviesRepository.getMovie(any()) }
        coVerify(exactly = 1) { favoritesRepository.observeFavorite(any()) }
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