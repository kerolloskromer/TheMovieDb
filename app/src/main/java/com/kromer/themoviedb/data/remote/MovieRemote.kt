package com.kromer.themoviedb.data.remote

import com.google.gson.annotations.SerializedName

data class MovieRemote(
    @SerializedName("id")
    val id: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String
)
