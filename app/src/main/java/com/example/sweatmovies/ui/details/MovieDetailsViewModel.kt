package com.example.sweatmovies.ui.details

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweatmovies.ui.details.uimodels.DetailsScreenState
import com.example.sweatmovies.ui.details.uimodels.DetailsScreenState.Companion.loadingTrailer
import com.example.sweatmovies.ui.details.uimodels.DetailsScreenState.Companion.make
import com.example.sweatmovies.ui.details.uimodels.MovieDetailsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: MovieDetailsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState.default)
    val state: StateFlow<DetailsScreenState>
        get() = _state
    fun getMovie(id: Int) = viewModelScope.launch {
        //it should never be null, error handling pending, maybe log it to a service
        val movie = useCase.getMovie(id) ?: return@launch
        _state.update {
            it.make(movie, false)
        }
    }

    //I would rather send something to the UI and handle the async here but I was having problems
    //and had to deliver this rather sooner than later
    suspend fun getTrailer(id: Int): String? {
        _state.update { it.loadingTrailer(true) }
        val trailer = useCase.getTrailer(id)
        _state.update { it.loadingTrailer(false) }
        return trailer
    }
}