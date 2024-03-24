package com.example.sweatmovies.ui.details.uimodels

import android.content.Intent

sealed class MovieDetailsAction {
    data object Nothing: MovieDetailsAction()

    data class Open(val intent: Intent = Intent()): MovieDetailsAction()
}
