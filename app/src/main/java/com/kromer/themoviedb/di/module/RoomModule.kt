package com.kromer.themoviedb.di.module

import android.content.Context
import androidx.room.Room
import com.kromer.themoviedb.data.local.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase =
        Room.databaseBuilder(context, MyDatabase::class.java, MyDatabase.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideMoviesDao(myDatabase: MyDatabase) = myDatabase.getMoviesDao()
}