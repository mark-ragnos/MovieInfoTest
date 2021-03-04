package com.example.movieinfotest.database

import Genres
import androidx.room.Database
import androidx.room.Insert
import androidx.room.RoomDatabase

@Database(entities = [Genres::class], version = 1)
abstract class GenresDatabase: RoomDatabase() {
    abstract fun genresDao(): GenreDao
    companion object{
        fun create(){
        }
    }

}