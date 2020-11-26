package com.kromer.themoviedb.domain.repository

import com.kromer.themoviedb.domain.model.Movie

/**
 * Interface to the data layer.
 */
interface MoviesRepository {
    suspend fun getPopularMovies(page: Int, forceUpdate: Boolean): List<Movie>
    suspend fun add(movies: List<Movie>)
    suspend fun get(id: String): Movie?
}