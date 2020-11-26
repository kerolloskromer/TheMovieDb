package com.kromer.themoviedb.data

import com.kromer.themoviedb.domain.model.Movie

/**
 * Interface to the data layer.
 */
interface MoviesRepository {
    suspend fun getPopularMovies(page: Int, forceUpdate: Boolean): List<Movie>
    suspend fun add(movies: List<Movie>)
}