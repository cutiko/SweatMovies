package com.example.sweatmovies.models

data class TrailersResponse(
    val results: List<Trailer> = emptyList()
) {
    fun trailer() = results.firstOrNull {
        it.youtubeUrl() != null
    }?.youtubeUrl()
}
