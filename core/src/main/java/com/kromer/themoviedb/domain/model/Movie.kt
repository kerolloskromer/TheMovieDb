package com.kromer.themoviedb.domain.model

data class Movie(
    val id: String,
    val popularity: Double,
    val voteAverage: Double,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String
)
