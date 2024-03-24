package com.example.sweatmovies.ui.details

import com.example.sweatmovies.repositories.MoviesRepository
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend fun getMovie(id: Int) = moviesRepository.getMovie(id)

    suspend fun getTrailer(id: Int) = moviesRepository.getTrailer(id)

}