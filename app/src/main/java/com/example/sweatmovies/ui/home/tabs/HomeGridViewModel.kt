package com.example.sweatmovies.ui.home.tabs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweatmovies.models.Movie
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeGridState
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeGridState.Companion.nowPlaying
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeGridState.Companion.selectTab
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeGridState.Companion.topRated
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeGridState.Companion.upcoming
import com.example.sweatmovies.ui.home.tabs.uimodels.HomeTabs
import com.example.sweatmovies.ui.home.usecases.GetMoviesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeGridViewModel @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeGridState.default)
    val state: StateFlow<HomeGridState>
        get() = _state

    fun observeMovies() {
        viewModelScope.launch {
            val movies = getMoviesByCategoryUseCase.getNowPlaying()
            _state.update { it.nowPlaying(movies) }
        }

        viewModelScope.launch {
            val movies = getMoviesByCategoryUseCase.getUpcoming()
            _state.update { it.upcoming(movies) }
        }

        viewModelScope.launch {
            val movies = getMoviesByCategoryUseCase.getTopRated()
            _state.update { it.topRated(movies) }
        }
    }

    fun selectTab(tab: HomeTabs) = _state.update {
        it.selectTab(tab)
    }

}