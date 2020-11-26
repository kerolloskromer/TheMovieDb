package com.kromer.themoviedb.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiInterface {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesResponse

}