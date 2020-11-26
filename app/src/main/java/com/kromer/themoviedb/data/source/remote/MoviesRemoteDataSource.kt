package com.kromer.themoviedb.data.source.remote

import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.domain.repository.MoviesDataSource
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val apiInterface: MoviesApiInterface
) :
    MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): List<Movie> =
        apiInterface.getPopularMovies(page).results

    override suspend fun add(movies: List<Movie>) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Movie? {
        TODO("Not yet implemented")
    }
}