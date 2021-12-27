package com.example.trainetestforsurf.di

import com.example.trainetestforsurf.data.movies.MoviesRemoteDataSource
import com.example.trainetestforsurf.data.movies.MoviesRemoteDataSourceImpl
import com.example.trainetestforsurf.data.movies.MoviesRepository
import com.example.trainetestforsurf.data.movies.MoviesRepositoryImpl
import com.example.trainetestforsurf.data.movies.core.MoviesHttpClient
import com.example.trainetestforsurf.data.movies.core.MoviesHttpClientImpl
import com.example.trainetestforsurf.data.movies.local.LocalDataSource
import com.example.trainetestforsurf.data.movies.local.LocalDataSourceImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindMoviesModule {

    @Binds
    fun bindMoviesRemoteDataSource(
        impl : MoviesRemoteDataSourceImpl
    ) : MoviesRemoteDataSource

    @Binds
    fun bindMoviesLocalDataSource(
        impl : LocalDataSourceImpl
    ) : LocalDataSource

    @Binds
    fun bindMoviesRepository(
        impl : MoviesRepositoryImpl
    ) : MoviesRepository

    @Binds
    fun moviesHttpClient(
        impl: MoviesHttpClientImpl,
    ) : MoviesHttpClient

}