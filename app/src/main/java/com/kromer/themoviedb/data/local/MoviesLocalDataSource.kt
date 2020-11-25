package com.kromer.themoviedb.data.local

import com.kromer.themoviedb.data.MoviesDataSource
import com.kromer.themoviedb.domain.model.MoviesResponse

class MoviesLocalDataSource() : MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): MoviesResponse {
        TODO("Not yet implemented")
    }
}