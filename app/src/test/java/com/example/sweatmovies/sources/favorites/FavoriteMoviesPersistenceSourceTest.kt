package com.example.sweatmovies.sources.favorites

import com.example.sweatmovies.db.FavoritesDao
import com.example.sweatmovies.db.MoviesDataBase
import com.example.sweatmovies.models.FavoriteMovie
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class FavoriteMoviesPersistenceSourceTest {

    private lateinit var moviesDataBase: MoviesDataBase
    private lateinit var favoritesDao: FavoritesDao
    private lateinit var favoritesLocalSource: FavoriteMoviesPersistenceSource

    @Before
    fun setup() {
        moviesDataBase = mockk()
        favoritesDao = mockk()
        every { moviesDataBase.favoritesDao() } returns favoritesDao
        favoritesLocalSource = FavoriteMoviesPersistenceSource(moviesDataBase)
    }

    @Test
    fun `test observeFavorites calls the DAO`() {
        every { favoritesDao.observeFavorites() } returns mockk()

        favoritesLocalSource.observeFavorites()

        verify(exactly = 1) { favoritesDao.observeFavorites() }
        confirmVerified(favoritesDao)
    }

    @Test
    fun `test addFavorite calls the DAO`() = runTest {
        val id = 123
        val expectedFavorite = FavoriteMovie(id)
        val favoriteSlot = slot<FavoriteMovie>()
        coEvery { favoritesDao.insert(capture(favoriteSlot)) } just Runs

        favoritesLocalSource.addFavorite(id)

        assertEquals(expectedFavorite, favoriteSlot.captured)
        coVerify(exactly = 1) { favoritesDao.insert(expectedFavorite) }
        confirmVerified(favoritesDao)
    }

    @Test
    fun `test deleteFavorite calls the DAO`() = runTest {
        val id = 123
        val idSlot = slot<Int>()
        coEvery { favoritesDao.delete(capture(idSlot)) } just Runs

        favoritesLocalSource.deleteFavorite(id)

        assertEquals(id, idSlot.captured)
        coVerify(exactly = 1) { favoritesDao.delete(id) }
        confirmVerified(favoritesDao)
    }

    @Test
    fun `test observeFavorite calls the DAO`() = runTest {
        val id = 123
        val idSlot = slot<Int>()
        every { favoritesDao.observeIsFavorite(capture(idSlot)) } returns MutableStateFlow(null)

        val obtained = favoritesLocalSource.observeFavorite(id).take(1).toList()

        assertEquals(id, idSlot.captured)
        verify(exactly = 1) { favoritesDao.observeIsFavorite(id) }
        confirmVerified(favoritesDao)
        assertFalse(obtained.first())
    }

}