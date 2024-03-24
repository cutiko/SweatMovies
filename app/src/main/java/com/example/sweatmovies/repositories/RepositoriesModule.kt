package com.example.sweatmovies.repositories

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
abstract class RepositoriesModule {
    @Binds
    abstract fun providesMoviesRepository(
        moviesNetworkSource: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    abstract fun providesMovieCategoriesRepository(
        movieCategoriesRepository: MovieCategoriesRepositoryImpl
    ): MovieCategoriesRepository
    @Binds
    abstract fun providesFavoritesRepository(
        favoritesRepository: FavoritesRepositoryImpl
    ): FavoritesRepository

}