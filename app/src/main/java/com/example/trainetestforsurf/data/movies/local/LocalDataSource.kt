package com.example.trainetestforsurf.data.movies.local

import com.example.trainetestforsurf.domain.Movies
import javax.inject.Inject

interface LocalDataSource {
    suspend fun saveMovie(movies: Movies)
    suspend fun isMovieFavourite(movies: Movies) : Boolean
}
class LocalDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : LocalDataSource{
    override suspend fun saveMovie(movies: Movies) {
        sharedPreferences.setFavorites(movies.id.toString(),movies.isFavorite)
    }

    override suspend fun isMovieFavourite(movies: Movies) : Boolean{
        return sharedPreferences.isMovieFavourite(movies.id.toString())
    }

}
