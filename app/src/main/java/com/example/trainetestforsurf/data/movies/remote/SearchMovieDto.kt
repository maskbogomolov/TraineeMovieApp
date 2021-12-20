package com.example.trainetestforsurf.data.movies.remote

import com.google.gson.annotations.SerializedName

data class SearchMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val image : String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?
)
