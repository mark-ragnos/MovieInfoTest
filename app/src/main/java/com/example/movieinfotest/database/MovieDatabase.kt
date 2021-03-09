package com.example.movieinfotest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieinfotest.database.genres.GenreDao
import com.example.movieinfotest.network.responses.details.Genre

@Database(entities = [Genre::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun genreDao():GenreDao
}