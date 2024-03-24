package com.example.sweatmovies.repositories

import com.example.sweatmovies.sources.favorites.FavoriteMoviesLocalSource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FavoritesRepositoryImplTest {

    private lateinit var localSource: FavoriteMoviesLocalSource
    private lateinit var repository: FavoritesRepositoryImpl

    @Before
    fun setup() {
        localSource = mockk()
        repository = FavoritesRepositoryImpl(localSource)
    }

    //region update
    @Test
    fun `test update calls addFavorite from source when favorite is true`() = runTest {
        coEvery { localSource.addFavorite(any()) } just Runs

        repository.update(true, 1)

        coVerify(exactly = 1) { localSource.addFavorite(any()) }
        confirmVerified(localSource)
    }

    @Test
    fun `test update calls deleteFavorite from source when favorite is false`() = runTest {
        coEvery { localSource.deleteFavorite(any()) } just Runs

        repository.update(false, 1)

        coVerify(exactly = 1) { localSource.deleteFavorite(any()) }
        confirmVerified(localSource)
    }
    //endregion

}