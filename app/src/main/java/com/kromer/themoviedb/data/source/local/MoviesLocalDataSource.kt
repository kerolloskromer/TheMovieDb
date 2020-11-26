package com.kromer.themoviedb.data.source.local

import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.domain.repository.MoviesDataSource
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao
) :
    MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): List<Movie> = moviesDao.get()

    override suspend fun add(movies: List<Movie>) = moviesDao.insert(movies)

    override suspend fun get(id: String): Movie? = moviesDao.get(id)
}