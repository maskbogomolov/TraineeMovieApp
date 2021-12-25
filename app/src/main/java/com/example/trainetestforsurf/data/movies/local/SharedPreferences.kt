package com.example.trainetestforsurf.data.movies.local

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    suspend fun isMovieFavourite(movieId: String): Boolean {
        return withContext(Dispatchers.Main) { sharedPreferences.getBoolean(movieId, false) }
    }

    suspend fun setFavorites(key: String, value: Boolean) {
        if (value) {
            withContext(Dispatchers.IO) { launch { sharedPreferences.edit().putBoolean(key, true).apply() } }
        } else {
            withContext(Dispatchers.IO) { launch { sharedPreferences.edit().remove(key).apply() } }
        }
    }
}