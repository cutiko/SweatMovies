package com.example.sweatmovies.sources.repositories

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MoviesResponse
import com.example.sweatmovies.network.MovieDBService
import com.example.sweatmovies.repositories.MoviesRepositoryImpl
import com.example.sweatmovies.sources.NetworkResult
import com.example.sweatmovies.sources.movies.MoviesLocalSource
import com.example.sweatmovies.sources.movies.MoviesNetworkSource
import io.mockk.Called
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRepositoryImplTest {

    private lateinit var remoteSource: MoviesNetworkSource
    private lateinit var localSource: MoviesLocalSource
    private lateinit var moviesRepository: MoviesRepositoryImpl

    @Before
    fun setup() {
        remoteSource = mockk()
        localSource = mockk()
        moviesRepository = MoviesRepositoryImpl(remoteSource, localSource)
    }

    //region getPopular
    @Test
    fun `test getPopular call the remote source`() = runTest {
        val expectedResult = NetworkResult.Error<MoviesResponse>()
        coEvery { remoteSource.getPopular() } returns expectedResult

        val obtained = moviesRepository.getPopularMovies()

        coVerify(exactly = 1) { remoteSource.getPopular() }
        verify { localSource wasNot Called }
        confirmVerified(remoteSource, localSource)
        assertEquals(expectedResult, obtained)
    }

    @Test
    fun `test getPopular call the remote and the local sources`() = runTest {
        val movies = listOf(Movie(), Movie(), Movie())
        val movieResponse = MoviesResponse(results = movies)
        val expectedResult = NetworkResult.Success(movieResponse)
        coEvery { remoteSource.getPopular() } returns expectedResult
        val moviesSlot = slot<List<Movie>>()
        coEvery { localSource.insertPopular(capture(moviesSlot)) } just Runs

        val obtained = moviesRepository.getPopularMovies()

        coVerify(exactly = 1) { remoteSource.getPopular() }
        assertEquals(movies, moviesSlot.captured)
        coVerify(exactly = 1) { localSource.insertPopular(movies) }
        coVerifyOrder {
            remoteSource.getPopular()
            localSource.insertPopular(movies)
        }
        confirmVerified(remoteSource, localSource)
        assertEquals(expectedResult, obtained)
    }
    //endregion

}