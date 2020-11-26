package com.kromer.themoviedb.domain.interactor

import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesByDateInteractor @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun getByDate(date: String): List<Movie> {
        return repository.getByDate(date)
    }
}