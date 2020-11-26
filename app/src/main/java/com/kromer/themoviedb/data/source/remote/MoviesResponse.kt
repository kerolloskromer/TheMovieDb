package com.kromer.themoviedb.data.source.remote

import com.google.gson.annotations.SerializedName
import com.kromer.themoviedb.domain.model.Movie

data class MoviesResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Movie>
)