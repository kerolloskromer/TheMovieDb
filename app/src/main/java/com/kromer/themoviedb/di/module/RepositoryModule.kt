package com.kromer.themoviedb.di.module

import com.kromer.themoviedb.domain.repository.MoviesRepository
import com.kromer.themoviedb.data.source.local.MoviesLocalDataSource
import com.kromer.themoviedb.data.source.remote.MoviesRemoteDataSource
import com.kromer.themoviedb.data.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        moviesLocalDataSource: MoviesLocalDataSource,
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(moviesLocalDataSource, moviesRemoteDataSource)
    }
}
