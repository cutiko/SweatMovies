package com.example.sweatmovies.sources.movies

import com.example.sweatmovies.network.MovieDBService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesNetworkSourceTest {

    private lateinit var retrofit: Retrofit
    private lateinit var moviesDBService: MovieDBService
    private lateinit var networkSource: MoviesNetworkSource

    @Before
    fun setup() {
        retrofit = mockk()
        moviesDBService = mockk()
        every { retrofit.create(MovieDBService::class.java) } returns moviesDBService
        networkSource = MoviesNetworkSource(retrofit)
    }
    @Test
    fun `test getPopular call the service`() = runTest {
        val popular = MovieDBService.Categories.Popular
        coEvery { moviesDBService.byCategory(popular.path) } returns mockk()

        networkSource.getByCategory(popular)

        coVerify(exactly = 1) { moviesDBService.byCategory(popular.path) }
        confirmVerified(moviesDBService)
    }

    @Test
    fun `test search call the service`() = runTest {
        val expectedQuery = "spiderman"
        val querySlot = slot<String>()
        coEvery { moviesDBService.search(
            query = capture(querySlot)
        ) } returns mockk()

        networkSource.search(expectedQuery)

        assertEquals(expectedQuery, querySlot.captured)
        coVerify(exactly = 1) { moviesDBService.search(expectedQuery) }
        confirmVerified(moviesDBService)
    }

    @Test
    fun `test trailers call the service`() = runTest {
        val expectedId = 2424
        val idSlot = slot<Int>()
        coEvery { moviesDBService.trailers(
            movieId = capture(idSlot)
        ) } returns mockk()

        networkSource.trailers(expectedId)

        assertEquals(expectedId, idSlot.captured)
        coVerify(exactly = 1) { moviesDBService.trailers(expectedId) }
        confirmVerified(moviesDBService)
    }

}