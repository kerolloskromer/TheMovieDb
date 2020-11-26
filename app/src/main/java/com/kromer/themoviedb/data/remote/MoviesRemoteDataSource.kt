package com.kromer.themoviedb.data.remote

import com.kromer.themoviedb.data.MoviesDataSource
import com.kromer.themoviedb.data.mapper.Mapper
import com.kromer.themoviedb.domain.model.Movie
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val apiInterface: MoviesApiInterface,
    private val mapper: Mapper
) :
    MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): List<Movie> =
        mapper.transformRemoteToModel(apiInterface.getPopularMovies(page).results)

    override suspend fun add(movies: List<Movie>) {
        TODO("Not yet implemented")
    }
}