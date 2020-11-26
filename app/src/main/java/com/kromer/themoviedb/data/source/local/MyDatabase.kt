package com.kromer.themoviedb.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kromer.themoviedb.domain.model.Movie

@Database(entities = [Movie::class], version = MyDatabase.DATABASE_VERSION)
abstract class MyDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MoviesDatabase"
    }

    abstract fun getMoviesDao(): MoviesDao
}