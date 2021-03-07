package com.example.movieinfotest.database.genres

import Genre
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Genre::class], version = 1)
abstract class GenresDatabase: RoomDatabase() {
    abstract fun genresDao(): GenreDao
    companion object{
        fun create(){
        }
    }

}