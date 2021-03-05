package com.example.movieinfotest.database.genres

import Genres
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieinfotest.database.genres.GenreDao

@Database(entities = [Genres::class], version = 1)
abstract class GenresDatabase: RoomDatabase() {
    abstract fun genresDao(): GenreDao
    companion object{
        fun create(){
        }
    }

}