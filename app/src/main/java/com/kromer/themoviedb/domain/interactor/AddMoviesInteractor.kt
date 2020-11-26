package com.kromer.themoviedb.domain.interactor

import com.kromer.themoviedb.domain.repository.MoviesRepository
import com.kromer.themoviedb.domain.model.Movie
import javax.inject.Inject

class AddMoviesInteractor @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun add(movies: List<Movie>) = repository.add(movies)
}