package com.example.movieinfotest.database

import Genre
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieinfotest.database.genres.GenreDao

@Database(entities = [Genre::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun genreDao():GenreDao
}