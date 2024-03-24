package com.example.sweatmovies.ui.home.carrousel.uimodels

import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem.Companion.update
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase.Result
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PopularCarrouselItemTest {

    @Test
    fun `test update returns loading item when the result is loading`() {
        val result = Result.Loading

        val default = PopularCarrouselItem.default
        val obtained = default.update(result)

        assertEquals(default, obtained)
    }

    @Test
    fun `test update returns movie items when the result is success`() {
        val movies = listOf(
            Movie(id = 1),
            Movie(id = 2)
        )
        val result = Result.Success(movies)

        val default = PopularCarrouselItem.default
        val obtained = default.update(result)

        assertEquals(movies.size, obtained.size)
        movies.forEachIndexed { index, movie ->
            val item = obtained[index] as PopularCarrouselItem.Movie
            assertEquals(movie.id, item.id)
            assertEquals(index + 1, item.position)
        }
    }

}