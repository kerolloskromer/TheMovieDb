package com.kromer.themoviedb.domain.interactor

import com.kromer.themoviedb.data.MoviesRepository
import com.kromer.themoviedb.domain.model.MoviesResponse

class GetPopularMoviesInteractor(
    private val repository: MoviesRepository
) {
    suspend fun getPopularMovies(
        page: Int
    ): MoviesResponse {
        return repository.getPopularMovies(page)
    }
}