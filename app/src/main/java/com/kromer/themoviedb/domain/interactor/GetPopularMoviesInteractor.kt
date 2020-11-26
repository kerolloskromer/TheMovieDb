package com.kromer.themoviedb.domain.interactor

import com.kromer.themoviedb.domain.repository.MoviesRepository
import com.kromer.themoviedb.domain.model.Movie
import javax.inject.Inject

class GetPopularMoviesInteractor @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun getPopularMovies(
        page: Int,
        forceUpdate: Boolean
    ): List<Movie> {
        return repository.getPopularMovies(page, forceUpdate)
    }
}