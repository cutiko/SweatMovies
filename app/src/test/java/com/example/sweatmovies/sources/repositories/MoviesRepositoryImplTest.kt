package com.example.sweatmovies.sources.repositories

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.models.MovieOverview
import com.example.sweatmovies.models.MoviesResponse
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
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

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

    //region fetchPopularMovies
    @Test
    fun `test fetchPopularMovies call the remote source`() = runTest {
        val expectedResult = NetworkResult.Error<MoviesResponse<Movie>>()
        coEvery { remoteSource.getPopular() } returns expectedResult

        val obtained = moviesRepository.fetchPopularMovies()

        coVerify(exactly = 1) { remoteSource.getPopular() }
        verify { localSource wasNot Called }
        confirmVerified(remoteSource, localSource)
        assertEquals(expectedResult, obtained)
    }

    @Test
    fun `test fetchPopularMovies call the remote and the local sources`() = runTest {
        val movies = listOf(Movie(), Movie(), Movie())
        val movieResponse = MoviesResponse(results = movies)
        val expectedResult = NetworkResult.Success(movieResponse)
        coEvery { remoteSource.getPopular() } returns expectedResult
        val moviesSlot = slot<List<Movie>>()
        coEvery { localSource.insertMovies(capture(moviesSlot)) } just Runs

        val obtained = moviesRepository.fetchPopularMovies()

        coVerify(exactly = 1) { remoteSource.getPopular() }
        assertEquals(movies, moviesSlot.captured)
        coVerify(exactly = 1) { localSource.insertMovies(movies) }
        coVerifyOrder {
            remoteSource.getPopular()
            localSource.insertMovies(movies)
        }
        confirmVerified(remoteSource, localSource)
        assertEquals(expectedResult, obtained)
    }
    //endregion

    @Test
    fun `test getLocalPopular calls de local source`() = runTest {
        val ids = listOf(1, 2, 3)
        val movies = ids.map { Movie(id = it) }
        val idsSlot = slot<List<Int>>()
        coEvery { localSource.getPopular(capture(idsSlot)) } returns movies

        val obtained = moviesRepository.getLocalPopular(ids)

        assertEquals(ids, idsSlot.captured)
        coVerify { localSource.getPopular(ids) }
        confirmVerified(localSource)
        assertEquals(movies, obtained)
    }

    @Test
    fun `test searchMovies calls de remote source`() = runTest {
        val expectedQuery = "spiderman"
        val querySlot = slot<String>()
        val result = NetworkResult.Error<MoviesResponse<MovieOverview>>()
        coEvery { remoteSource.search(capture(querySlot)) } returns result

        val obtained = moviesRepository.searchMovies(expectedQuery)

        assertEquals(expectedQuery, querySlot.captured)
        coVerify { remoteSource.search(expectedQuery) }
        confirmVerified(remoteSource)
        assertEquals(result, obtained)
    }

}