package com.kromer.themoviedb.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kromer.themoviedb.domain.model.Movie

/**
 * Data Access Object for the movies table.
 */
@Dao
interface MoviesDao {
    /**
     * Select all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movies")
    suspend fun get(): List<Movie>

    /**
     * Select a movie by id.
     *
     * @param id the movie id.
     * @return the movie with id.
     */
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun get(id: String): Movie?

    /**
     * Insert movies in the database. If the movie already exists, replace it.
     *
     * @param movies the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)
}