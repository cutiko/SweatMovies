package com.example.sweatmovies.sources

import com.example.sweatmovies.sources.categories.MoviesCategoriesLocalSource
import com.example.sweatmovies.sources.categories.MoviesCategoriesPersistenceSource
import com.example.sweatmovies.sources.movies.MoviesLocalSource
import com.example.sweatmovies.sources.movies.MoviesNetworkSource
import com.example.sweatmovies.sources.movies.MoviesPersistenceSource
import com.example.sweatmovies.sources.movies.MoviesRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {
    @Binds
    abstract fun providesMoviesRemoteSource(
        moviesNetworkSource: MoviesNetworkSource
    ): MoviesRemoteSource

    @Binds
    abstract fun providesMoviesLocalSource(
        moviesLocalSource: MoviesPersistenceSource
    ): MoviesLocalSource

    @Binds
    abstract fun providesMoviesCategoriesLocalSource(
        moviesCategoriesLocalSource: MoviesCategoriesPersistenceSource
    ): MoviesCategoriesLocalSource

}