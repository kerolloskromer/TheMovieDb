package com.kromer.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieLocal::class], version = MyDatabase.DATABASE_VERSION)
abstract class MyDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MoviesDatabase"
    }

    abstract fun getMoviesDao(): MoviesDao
}