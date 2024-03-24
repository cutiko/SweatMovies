package com.example.sweatmovies.ui.home.carrousel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem
import com.example.sweatmovies.ui.home.carrousel.uimodels.PopularCarrouselItem.Companion.update
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase
) : ViewModel() {

    private val _carrouselItems = MutableStateFlow(PopularCarrouselItem.default)
    val carrouselItems: StateFlow<List<PopularCarrouselItem>>
        get() = _carrouselItems
    fun startObserving() = viewModelScope.launch {
        getMoviesByCategoryUseCase.observePopularMovies().collect { result ->
            _carrouselItems.update { it.update(result) }
        }
    }

}