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
        coEvery { moviesDBService.getPopular() } returns mockk()

        networkSource.getPopular()

        coVerify(exactly = 1) { moviesDBService.getPopular() }
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

}