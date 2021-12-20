package com.example.trainetestforsurf.di

import com.example.trainetestforsurf.data.movies.MoviesRemoteDataSource
import com.example.trainetestforsurf.data.movies.MoviesRemoteDataSourceImpl
import com.example.trainetestforsurf.data.movies.MoviesRepository
import com.example.trainetestforsurf.data.movies.MoviesRepositoryImpl

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
    fun bindMoviesRepository(
        impl : MoviesRepositoryImpl
    ) : MoviesRepository
}