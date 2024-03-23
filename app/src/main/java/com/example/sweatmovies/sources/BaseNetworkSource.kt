package com.example.sweatmovies.sources

import retrofit2.Response

interface BaseNetworkSource {

    suspend fun <Type>request(
        block: suspend () -> Response<Type>
    ): NetworkResult<Type> {
        return try {
            val response = block.invoke()
            if (response.isSuccessful) {
                val data = response.body() ?: return NetworkResult.Error()
                NetworkResult.Success(data)
            } else {
                NetworkResult.Error(code = response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(exception = e)
        }
    }

}