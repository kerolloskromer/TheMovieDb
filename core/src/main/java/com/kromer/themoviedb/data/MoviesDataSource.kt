package com.kromer.themoviedb.data

import com.kromer.themoviedb.domain.model.MoviesResponse

/**
 * Main entry point for accessing movies data.
 */
interface MoviesDataSource {
    suspend fun getPopularMovies(page: Int): MoviesResponse
}