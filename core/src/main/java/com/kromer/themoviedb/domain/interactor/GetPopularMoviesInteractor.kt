package com.kromer.themoviedb.domain.interactor

import com.kromer.themoviedb.data.MoviesRepository
import com.kromer.themoviedb.domain.model.Movie

class GetPopularMoviesInteractor(
    private val repository: MoviesRepository
) {
    suspend fun getPopularMovies(
        page: Int,
        forceUpdate: Boolean
    ): List<Movie> {
        return repository.getPopularMovies(page, forceUpdate)
    }
}