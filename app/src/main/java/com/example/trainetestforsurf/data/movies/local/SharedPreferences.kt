package com.example.trainetestforsurf.data.movies.local

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    val mutex = Mutex()


    suspend fun isMovieFavourite(movieId: String): Boolean {
        mutex.withLock {
            return sharedPreferences.getBoolean(movieId, false)
        }
    }

    suspend fun setFavorites(key: String, value: Boolean) {
        mutex.withLock {
            if (value) {
                withContext(Dispatchers.IO) { sharedPreferences.edit().putBoolean(key, true).commit() }
            } else {
                withContext(Dispatchers.IO) { sharedPreferences.edit().remove(key).commit() }
            }
        }

    }
}