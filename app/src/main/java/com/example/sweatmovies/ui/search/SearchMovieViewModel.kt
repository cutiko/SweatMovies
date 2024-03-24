package com.example.sweatmovies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweatmovies.ui.search.uimodels.SearchResultsScreenState
import com.example.sweatmovies.ui.search.uimodels.SearchResultsScreenState.Companion.updateResults
import com.example.sweatmovies.ui.search.uimodels.SearchResultsScreenState.Companion.updateUserInput
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

    private val _screenState = MutableStateFlow(SearchResultsScreenState.default)
    val screenState: StateFlow<SearchResultsScreenState>
        get() = _screenState

    fun search(term: String) = viewModelScope.launch {
        _screenState.update { it.updateUserInput(term) }
        val overviews = searchMoviesUseCase.byTerm(term)
        _screenState.update { it.updateResults(overviews) }
    }

}