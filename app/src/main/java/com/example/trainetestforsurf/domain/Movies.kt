package com.example.trainetestforsurf.domain

data class Movies(
    val id : Int,
    val title : String,
    val image : String ,
    val overview : String,
    val releaseDate : String,
    var isFavorite : Boolean
)