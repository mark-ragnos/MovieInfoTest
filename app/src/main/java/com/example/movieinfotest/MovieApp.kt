package com.example.movieinfotest

import android.app.Application
import androidx.room.Room
import com.example.movieinfotest.database.MovieDatabase

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(instance, MovieDatabase::class.java, "db_movie33.db").build()
    }

    fun getDatabase(): MovieDatabase {
        return database
    }

    companion object {
        private lateinit var database: MovieDatabase
        private lateinit var instance: MovieApp


        fun getInstance(): MovieApp {
            return instance
        }
    }
}