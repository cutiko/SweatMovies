package com.example.sweatmovies.sources

import com.example.sweatmovies.network.MovieDBService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
class BaseNetworkSourceTest {

    private lateinit var baseNetworkSource: BaseNetworkSource

    @Before
    fun setup() {
        baseNetworkSource = BaseNetworkSourceImpl()
    }
    @Test
    fun `test request returns error when block throws exception`() = runTest {
        val exceptionFunction: suspend () -> Response<String> = suspend {
            throw RuntimeException()
        }

        val result = baseNetworkSource.request { exceptionFunction() }

        when(result) {
            is NetworkResult.Error -> {
                val exception = result.exception as? RuntimeException
                assertNotNull(exception)
                assertEquals(NetworkResult.UNKNOWN_ERROR, result.code)
            }
            is NetworkResult.Success -> fail("Was expecting error obtained Success")
        }
    }

    @Test
    fun `test request returns error when response is not succesful`() = runTest {
        val response: Response<String> = mockk()
        every { response.isSuccessful } returns false
        every { response.code() } returns EXPECTED_CODE
        val errorFunction: suspend () -> Response<String> = suspend { response }

        val result = baseNetworkSource.request { errorFunction() }

        when(result) {
            is NetworkResult.Error -> {
                assertEquals(EXPECTED_CODE, result.code)
            }
            is NetworkResult.Success -> fail("Was expecting error obtained Success")
        }
    }

    @Test
    fun `test request returns error when response is successful but data is null`() = runTest {
        val response: Response<String> = mockk()
        every { response.isSuccessful } returns true
        every { response.body() } returns null
        val nullDataFunction: suspend () -> Response<String> = suspend { response }

        val result = baseNetworkSource.request { nullDataFunction() }

        when(result) {
            is NetworkResult.Error -> {
                assertEquals(NetworkResult.Error<String>(), result)
            }
            is NetworkResult.Success -> fail("Was expecting error obtained Success")
        }
    }

    @Test
    fun `test request returns success when response is successful`() = runTest {
        val response: Response<String> = mockk()
        every { response.isSuccessful } returns true
        val expectedData = "EXPECTED_DATA"
        every { response.body() } returns expectedData
        val successFunction: suspend () -> Response<String> = suspend { response }

        val result = baseNetworkSource.request { successFunction() }

        when(result) {
            is NetworkResult.Error -> fail("Was expecting Success obtained $result")
            is NetworkResult.Success -> {
                val expected = NetworkResult.Success(data = expectedData)
                assertEquals(expected, result)
            }
        }
    }

    private class BaseNetworkSourceImpl : BaseNetworkSource

    companion object {
        private const val EXPECTED_CODE = 411
    }

}