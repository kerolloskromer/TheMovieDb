package com.kromer.themoviedb.domain.interactor

import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieInteractor @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun getMovie(id: String): Movie? = repository.get(id)
}