package com.example.sweatmovies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem
import com.example.sweatmovies.ui.search.uimodels.SearchResultItem.Companion.update
import com.example.sweatmovies.ui.search.usecases.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
): ViewModel() {

    private val _resultItems = MutableStateFlow(SearchResultItem.default)
    val resultItems: StateFlow<List<SearchResultItem>>
        get() = _resultItems


    fun search(term: String) = viewModelScope.launch {
        val overviews = searchMoviesUseCase.byTerm(term)
        _resultItems.update { it.update(overviews) }
    }

}