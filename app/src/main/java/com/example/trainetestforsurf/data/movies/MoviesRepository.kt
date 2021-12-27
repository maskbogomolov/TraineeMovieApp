package com.example.trainetestforsurf.data.movies


import com.example.trainetestforsurf.data.movies.local.LocalDataSource
import com.example.trainetestforsurf.data.movies.remote.toMainMovies
import com.example.trainetestforsurf.data.movies.remote.toSearchMovies
import com.example.trainetestforsurf.domain.Movies
import com.example.trainetestforsurf.util.NetworkResult
import com.example.trainetestforsurf.util.mapResponse
import javax.inject.Inject

interface MoviesRepository {

    suspend fun getMovies(): NetworkResult<List<Movies>, Throwable>
    suspend fun searchMovies(query: String): NetworkResult<List<Movies>, Throwable>
    suspend fun saveMovie(movies: Movies)
    suspend fun isMovieFavourite(movies: Movies) : Boolean
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource : LocalDataSource

) : MoviesRepository {
    override suspend fun getMovies(): NetworkResult<List<Movies>, Throwable> {
        return moviesRemoteDataSource.getMovies()
            .mapResponse { entity -> entity.map { it.toMainMovies() } }
    }

    override suspend fun searchMovies(query: String): NetworkResult<List<Movies>, Throwable> {
        return moviesRemoteDataSource.searchMovies(query)
            .mapResponse { searchDto -> searchDto.map { it.toSearchMovies() } }
    }

    override suspend fun saveMovie(movies: Movies) {
        moviesLocalDataSource.saveMovie(movies)
    }

    override suspend fun isMovieFavourite(movies: Movies): Boolean {
        return moviesLocalDataSource.isMovieFavourite(movies)
    }


}