package com.kromer.themoviedb.data.local

import com.kromer.themoviedb.data.MoviesDataSource
import com.kromer.themoviedb.data.mapper.Mapper
import com.kromer.themoviedb.domain.model.Movie
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val mapper: Mapper
) :
    MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): List<Movie> =
        mapper.transformLocalToModel(moviesDao.get())

    override suspend fun add(movies: List<Movie>) {
        moviesDao.insert(mapper.transformModelToLocal(movies))
    }
}