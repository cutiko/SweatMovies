package com.example.sweatmovies.ui.home.tabs.uimodels

import androidx.annotation.StringRes
import com.example.sweatmovies.R

enum class HomeTabs(@StringRes val title: Int) {
    NowPlaying(R.string.home_tab_now_playing),
    Upcoming(R.string.home_tab_upcoming),
    TopRated(R.string.home_tab_top_rated)
}