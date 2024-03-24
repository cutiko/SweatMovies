package com.example.sweatmovies.ui.search.uimodels

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.uimodels.MovieResultItem
import com.example.sweatmovies.ui.uimodels.MovieResultItem.Companion.addLoading
import com.example.sweatmovies.ui.uimodels.MovieResultItem.Companion.update
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MovieResultItemTest {

    @Test
    fun `test update returns a list of SearchResultItem`() {
        val movies = listOf(
            Movie(id = 1),
            Movie(id = 2),
            Movie(id = 3)
        )

        val original = MovieResultItem.default
        val updated = original.update(movies)

        assertEquals(original.size, 0)
        assertEquals(movies.size, updated.size)
        movies.forEachIndexed { index, overview ->
            val item = updated[index] as MovieResultItem.Overview
            assertEquals(overview.id, item.id)
        }
    }

    @Test
    fun `test addLoading appends a loading at the beginning`() {
        val movies = listOf(Movie(id = 1))

        val original = MovieResultItem.default.update(movies)
        val updated = original.addLoading()

        assertEquals(movies.size + 1, updated.size)
        val loading = updated.first() as MovieResultItem.Loading
        assertEquals(MovieResultItem.Loading, loading)
    }

}