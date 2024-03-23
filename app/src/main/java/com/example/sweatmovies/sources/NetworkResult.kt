package com.example.sweatmovies.sources

import java.lang.Exception

sealed class NetworkResult<Type> {
    data class Success<Type>(
        val data: Type
    ): NetworkResult<Type>()

    data class Error<Type>(
        val code: Int = UNKNOWN_ERROR,
        val exception: Exception? = null
    ) : NetworkResult<Type>()

    companion object {
        const val UNKNOWN_ERROR = -1
    }
}