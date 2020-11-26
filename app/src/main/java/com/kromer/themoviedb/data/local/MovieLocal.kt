package com.kromer.themoviedb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey
    val id: String,
    val popularity: Double,
    val voteAverage: Double,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String
)
