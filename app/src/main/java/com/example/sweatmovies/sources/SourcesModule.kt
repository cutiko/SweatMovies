package com.example.sweatmovies.sources

import com.example.sweatmovies.sources.movies.MoviesNetworkSource
import com.example.sweatmovies.sources.movies.MoviesRemoteSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {
    @Binds
    abstract fun providesMoviesRemoteSource(
        moviesNetworkSource: MoviesNetworkSource
    ): MoviesRemoteSource

}