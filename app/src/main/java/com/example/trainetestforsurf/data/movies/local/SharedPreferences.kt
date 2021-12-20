package com.example.trainetestforsurf.data.movies.local

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun isMovieFavourite(movieId: String) : Boolean{
        return sharedPreferences.getBoolean(movieId, false)
    }

    fun setFavorites(key : String,value : Boolean){
        if (value){
            sharedPreferences.edit().putBoolean(key,true).apply()
        }else{
            sharedPreferences.edit().remove(key).apply()
        }
    }
}