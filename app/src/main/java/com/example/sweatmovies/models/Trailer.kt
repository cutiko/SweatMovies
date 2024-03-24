package com.example.sweatmovies.models

//site and type should be enums but Im running against time
data class Trailer(
    val key: String = "",
    val site: String = "",
    val type: String = ""
) {

    val youtubeUrl = if (YOUTUBE_TYPE == site && key.isNotBlank()) {
        "$YOUTUBE_BASE_URL$key"
    } else null

    val isTrailer = TRAILER_TYPE == type

    companion object {
        const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
        const val YOUTUBE_TYPE = "YouTube"
        const val TRAILER_TYPE = "Trailer"
    }
}