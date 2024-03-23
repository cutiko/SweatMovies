package com.example.sweatmovies.db

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    fun providesDb(@ApplicationContext context: Context): MoviesDataBase {
        return DatabaseProvider.db(context)
    }

}