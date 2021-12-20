package com.example.trainetestforsurf.di


import com.example.trainetestforsurf.data.movies.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMovies() : MoviesApi{
        return MoviesApi.create()
    }
}