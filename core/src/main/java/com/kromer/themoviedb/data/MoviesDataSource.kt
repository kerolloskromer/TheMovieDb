package com.kromer.themoviedb.data

import com.kromer.themoviedb.domain.model.Movie

/**
 * Main entry point for accessing movies data.
 */
interface MoviesDataSource {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun add(movies: List<Movie>)
}