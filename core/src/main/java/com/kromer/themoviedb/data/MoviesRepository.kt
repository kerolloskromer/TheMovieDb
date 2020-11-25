package com.kromer.themoviedb.data

import com.kromer.themoviedb.domain.model.MoviesResponse

/**
 * Interface to the data layer.
 */
interface MoviesRepository {
    suspend fun getPopularMovies(page: Int): MoviesResponse
}