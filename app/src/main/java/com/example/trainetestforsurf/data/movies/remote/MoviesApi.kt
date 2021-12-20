package com.example.trainetestforsurf.data.movies.remote

import android.util.Log
import com.example.trainetestforsurf.data.movies.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "c058d9a291e7f1dd69f97f1afac69b61"
interface MoviesApi {

    @GET("discover/movie?api_key=c058d9a291e7f1dd69f97f1afac69b61")
    suspend fun getMovies(
    ) : MoviesResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query : String,
        @Query("api_key") apiKey : String = API_KEY,
    ) : SearchMovieResponse

    companion object {

        fun create(): MoviesApi {
            Log.d("MoviesApi","create")
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesApi::class.java)
        }
    }
}