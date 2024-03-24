package com.example.sweatmovies.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweatmovies.ui.uimodels.MovieResultItem
import com.example.sweatmovies.ui.uimodels.MovieResultItem.Companion.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val watchListUseCase: WatchListUseCase
) : ViewModel() {

    private val _items = MutableStateFlow(MovieResultItem.default)
    val items: StateFlow<List<MovieResultItem>>
        get() = _items

    fun observeFavorites() = viewModelScope.launch {
        watchListUseCase.observeFavorites().collect { movies ->
            _items.update { it.update(movies) }
        }
    }

}