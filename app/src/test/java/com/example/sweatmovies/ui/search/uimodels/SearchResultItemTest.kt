package com.example.sweatmovies.ui.search.uimodels

import com.example.sweatmovies.models.MovieOverview
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.addLoading
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.update
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SearchResultItemTest {

    @Test
    fun `test update returns a list of SearchResultItem`() {
        val overviews = listOf(
            MovieOverview(id = 1),
            MovieOverview(id = 2),
            MovieOverview(id = 3)
        )

        val original = SearchResultItem.default
        val updated = original.update(overviews)

        assertEquals(original.size, 1)
        assertEquals(listOf(SearchResultItem.Loading), original)
        assertEquals(overviews.size, updated.size)
        overviews.forEachIndexed { index, overview ->
            val item = updated[index] as SearchResultItem.Overview
            assertEquals(overview.id, item.id)
        }
    }

    @Test
    fun `test addLoading appends a loading at the beginning`() {
        val overviews = listOf(MovieOverview(id = 1))

        val original = SearchResultItem.default.update(overviews)
        val updated = original.addLoading()

        assertEquals(overviews.size + 1, updated.size)
        val loading = updated.first() as SearchResultItem.Loading
        assertEquals(SearchResultItem.Loading, loading)
    }

}