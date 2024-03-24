package com.example.sweatmovies.network

import com.example.sweatmovies.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val TOKEN = BuildConfig.MOVIES_KEY

    fun retrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header(
                "accept",
                "application/json"
            )
            requestBuilder.header(
                "Authorization",
                "Bearer $TOKEN"
            )
            chain.proceed(requestBuilder.build())
        }
        val client = clientBuilder.build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonFactory())
            .build()
    }

    private fun gsonFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return GsonConverterFactory.create(gson)
    }

}