package com.example.trainetestforsurf.data.movies.core


import com.example.trainetestforsurf.data.movies.remote.MoviesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


interface MoviesHttpClient {
    val api : MoviesApi
}
class MoviesHttpClientImpl @Inject constructor() : MoviesHttpClient{

    val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    val client = OkHttpClient.Builder()
        .addInterceptor(QueryInterceptor.QueryInterceptor(hashMapOf("api_key" to "c058d9a291e7f1dd69f97f1afac69b61")))
        .addInterceptor(logger)
        .build()


    val retrofit =  Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override val api: MoviesApi by lazy {retrofit.create(MoviesApi::class.java)}

}