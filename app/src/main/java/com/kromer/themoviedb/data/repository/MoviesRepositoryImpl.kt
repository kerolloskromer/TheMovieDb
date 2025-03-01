package com.kromer.themoviedb.data.repository

import com.kromer.themoviedb.data.source.local.MoviesLocalDataSource
import com.kromer.themoviedb.data.source.remote.MoviesRemoteDataSource
import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {
    override suspend fun getPopularMovies(
        page: Int,
        forceUpdate: Boolean,
        includeAdult: Boolean
    ): List<Movie> {
        return if (forceUpdate) {
            val items = moviesRemoteDataSource.getPopularMovies(page, includeAdult)
            add(items)
            items
        } else {
            moviesLocalDataSource.getPopularMovies(page, includeAdult)
        }
    }

    override suspend fun getByDate(date: String): List<Movie> =
        moviesLocalDataSource.getByDate(date)

    override suspend fun add(movies: List<Movie>) = moviesLocalDataSource.add(movies)

    override suspend fun get(id: String): Movie? = moviesLocalDataSource.get(id)
}