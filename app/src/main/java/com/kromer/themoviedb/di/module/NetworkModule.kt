package com.kromer.themoviedb.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kromer.themoviedb.BuildConfig
import com.kromer.themoviedb.data.source.remote.MoviesApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val REQUEST_TIME_OUT: Long = 60

    @Provides
    @Singleton
    fun provideHeadersInterceptor(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val httpBuilder = request.url.newBuilder()
            httpBuilder.addQueryParameter("api_key", BuildConfig.API_KEY)
            httpBuilder.addQueryParameter(
                "language",
                context.resources.configuration.locale.language
            )
            val newUrl = httpBuilder.build()
            val newRequest = request.newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        .addNetworkInterceptor(headersInterceptor)
        .addNetworkInterceptor(logging)
        .retryOnConnectionFailure(false)
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls() // to allow sending null values
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideMoviesApiInterface(retrofit: Retrofit): MoviesApiInterface =
        retrofit.create(MoviesApiInterface::class.java)
}