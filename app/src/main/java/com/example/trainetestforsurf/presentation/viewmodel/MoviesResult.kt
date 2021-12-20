package com.example.trainetestforsurf.presentation.viewmodel

import com.example.trainetestforsurf.domain.Movies
import com.example.trainetestforsurf.domain.SearchMovies

sealed class MoviesResult {

    object Loading : MoviesResult()
    object EmptyQuery : MoviesResult()
    data class EmptyResult(val request : String) : MoviesResult()
    data class SuccessResult(val result: List<Movies>) : MoviesResult()
    data class SuccessSearchResult(val result: List<Movies>) : MoviesResult()
    data class ErrorSearchResult(val query: String) : MoviesResult()
    data class ErrorResult(val e: Throwable) : MoviesResult()
}