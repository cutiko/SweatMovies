package com.example.sweatmovies.db

import androidx.room.TypeConverter

//Don't want to add Gson to the DI for speed sake so keeping it simple
//Creating a new Gson every time make DB transactions slow
class MoviesConverters {

    @TypeConverter
    fun intListToString(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToIntList(string: String): List<Int> {
        return string.split(",").map {
            it.toIntOrNull() ?: 0
        }
    }

}