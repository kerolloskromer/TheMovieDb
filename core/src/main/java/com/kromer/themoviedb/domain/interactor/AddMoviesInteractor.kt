package com.kromer.themoviedb.domain.interactor

import com.kromer.themoviedb.data.MoviesRepository
import com.kromer.themoviedb.domain.model.Movie

class AddMoviesInteractor(
    private val repository: MoviesRepository
) {
    suspend fun add(movies: List<Movie>) = repository.add(movies)
}