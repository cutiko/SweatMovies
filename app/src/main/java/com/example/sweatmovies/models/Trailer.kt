package com.example.sweatmovies.models

//site and type should be enums but Im running against time
data class Trailer(
    val key: String = "",
    val site: String = "",
    val type: String = ""
) {

    fun youtubeUrl() = if (YOUTUBE_SITE == site && key.isNotEmpty()) {
        "$YOUTUBE_BASE_URL$key"
    } else null

    companion object {
        const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
        const val YOUTUBE_SITE = "YouTube"
    }
}