package com.kromer.themoviedb.domain.repository

import com.kromer.themoviedb.domain.model.Movie

/**
 * Main entry point for accessing movies data.
 */
interface MoviesDataSource {
    suspend fun getPopularMovies(page: Int, includeAdult: Boolean): List<Movie>
    suspend fun getByDate(date: String): List<Movie>
    suspend fun add(movies: List<Movie>)
    suspend fun get(id: String): Movie?
}