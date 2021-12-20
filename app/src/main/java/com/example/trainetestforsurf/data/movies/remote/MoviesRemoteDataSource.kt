package com.example.trainetestforsurf.data.movies

import android.util.Log
import com.example.trainetestforsurf.data.movies.remote.MoviesApi
import com.example.trainetestforsurf.data.movies.remote.MoviesDto
import com.example.trainetestforsurf.data.movies.remote.SearchMovieDto
import com.example.trainetestforsurf.util.NetworkResult
import javax.inject.Inject


interface MoviesRemoteDataSource {

    suspend fun getMovies() : NetworkResult<List<MoviesDto>,Throwable>
    suspend fun searchMovies(query : String) : NetworkResult<List<SearchMovieDto>,Throwable>

}

class MoviesRemoteDataSourceImpl @Inject constructor(private val moviesApi: MoviesApi) : MoviesRemoteDataSource{
    override suspend fun getMovies(): NetworkResult<List<MoviesDto>, Throwable> {
        return try {
            val movies = moviesApi.getMovies().result
            NetworkResult.Success(movies)
        }catch (e:Throwable){

            NetworkResult.Error("Not found movies")
        }
    }

    override suspend fun searchMovies(query : String): NetworkResult<List<SearchMovieDto>, Throwable> {
        return try {
            val searchMovies = moviesApi.searchMovie(query).searchMovies
            Log.d("MyRepo","succes")
            NetworkResult.Success(searchMovies)
        }catch (e : Throwable){
            Log.d("MyRepo","error")
            NetworkResult.Error("Not found movies")
        }
    }

}