package com.example.trainetestforsurf.presentation.viewmodel

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.*
import com.example.trainetestforsurf.data.movies.MoviesRepository
import com.example.trainetestforsurf.data.movies.local.SharedPreferences
import com.example.trainetestforsurf.domain.Movies
import com.example.trainetestforsurf.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")
    private val _searchMovie = MutableStateFlow<MoviesResult>(MoviesResult.EmptyQuery)
    val searchMovie: LiveData<MoviesResult>
        get() = _searchMovie.asLiveData(viewModelScope.coroutineContext)

    private val _listMovie = MutableLiveData<MoviesResult>()
    val listMovie: LiveData<MoviesResult>
        get() = _listMovie

    init {
        viewModelScope.launch {
            queryFlow
                .sample(500L)
                .onEach { _searchMovie.value = MoviesResult.Loading }
                .mapLatest(::handleQuery)
                .collect { state -> _searchMovie.value = state }
        }
        loadListMovie()
    }

    fun onNewQuery(query: String) {
        queryFlow.value = query
    }

    fun loadListMovie() = viewModelScope.launch {
        handleListMovie()
    }

    suspend fun handleQuery(query: String): MoviesResult {
        return if (query.isEmpty()) {
            MoviesResult.EmptyQuery
        } else {
            handleSearchMovie(query)
        }
    }

    private suspend fun handleSearchMovie(query: String): MoviesResult {
        return when (val movieResult = repository.searchMovies(query)) {
            is NetworkResult.Error -> {
                MoviesResult.ErrorSearchResult(query)
            }
            is NetworkResult.Success -> {
                if (movieResult.data.isEmpty()) MoviesResult.EmptyResult(query) else MoviesResult.SuccessSearchResult(
                    movieResult.data
                )
            }
        }
    }

    private suspend fun handleListMovie() {
        return when (val movieResult = repository.getMovies()) {
            is NetworkResult.Error -> _listMovie.value =
                MoviesResult.ErrorResult(IllegalArgumentException("Search movies from server error!"))
            is NetworkResult.Success -> {
                _listMovie.value = MoviesResult.SuccessResult(movieResult.data)
            }
        }

    }

    fun saveMovie(movies: Movies) {
        viewModelScope.launch {
            repository.saveMovie(movies)
        }
    }

    fun isMovieFavourite(movies: Movies) {
        viewModelScope.launch {
            movies.isFavorite = repository.isMovieFavourite(movies)
        }
    }
}