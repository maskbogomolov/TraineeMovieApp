package com.example.trainetestforsurf.data.movies.remote

import com.example.trainetestforsurf.domain.Movies
import com.example.trainetestforsurf.domain.SearchMovies

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
internal fun MoviesDto.toMainMovies(): Movies {
    return Movies(
        id = this.id,
        image = setImage(BASE_IMAGE_URL,this.image),
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
        title = this.title ?: "",
        isFavorite = false
    )
}
fun setImage(baseImageUrl : String,poster : String?) : String = "${baseImageUrl}${poster}"
internal fun SearchMovieDto.toSearchMovies() : Movies{
    return Movies(
        id = this.id,
        image = setImage(BASE_IMAGE_URL,this.image),
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
        title = this.title ?: "",
        isFavorite = false
    )
}
