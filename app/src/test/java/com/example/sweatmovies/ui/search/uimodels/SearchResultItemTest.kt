package com.example.sweatmovies.ui.search.uimodels

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.addLoading
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.update
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SearchResultItemTest {

    @Test
    fun `test update returns a list of SearchResultItem`() {
        val movies = listOf(
            Movie(id = 1),
            Movie(id = 2),
            Movie(id = 3)
        )

        val original = SearchResultItem.default
        val updated = original.update(movies)

        assertEquals(original.size, 0)
        assertEquals(movies.size, updated.size)
        movies.forEachIndexed { index, overview ->
            val item = updated[index] as SearchResultItem.Overview
            assertEquals(overview.id, item.id)
        }
    }

    @Test
    fun `test addLoading appends a loading at the beginning`() {
        val movies = listOf(Movie(id = 1))

        val original = SearchResultItem.default.update(movies)
        val updated = original.addLoading()

        assertEquals(movies.size + 1, updated.size)
        val loading = updated.first() as SearchResultItem.Loading
        assertEquals(SearchResultItem.Loading, loading)
    }

}