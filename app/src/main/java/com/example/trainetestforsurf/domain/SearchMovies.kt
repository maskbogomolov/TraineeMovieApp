package com.example.trainetestforsurf.domain

import com.google.gson.annotations.SerializedName

data class SearchMovies(
    @SerializedName("poster_path")
    val image : String?,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String
)