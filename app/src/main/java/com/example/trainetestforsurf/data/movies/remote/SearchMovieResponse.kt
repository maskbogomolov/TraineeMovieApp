package com.example.trainetestforsurf.data.movies.remote

import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val searchMovies: List<SearchMovieDto>
)
