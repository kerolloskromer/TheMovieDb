package com.kromer.themoviedb.data.remote

import com.kromer.themoviedb.data.MoviesDataSource
import com.kromer.themoviedb.domain.model.MoviesResponse

class MoviesRemoteDataSource() : MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): MoviesResponse {
        TODO("Not yet implemented")
    }
}