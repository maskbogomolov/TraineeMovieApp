package com.example.trainetestforsurf.data.movies.remote

import android.util.Log
import com.example.trainetestforsurf.data.movies.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {

    @GET("discover/movie")
    suspend fun getMovies(
    ) : MoviesResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query : String,
    ) : SearchMovieResponse


}