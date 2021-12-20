package com.example.trainetestforsurf.data.movies

import com.example.trainetestforsurf.data.movies.remote.MoviesDto
import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @SerializedName("page")
    val page : Int,
    @SerializedName("results")
    val result : List<MoviesDto>
)

