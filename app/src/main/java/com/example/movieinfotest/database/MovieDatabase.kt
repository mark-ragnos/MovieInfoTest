package com.example.movieinfotest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieinfotest.database.actors.ActorDao
import com.example.movieinfotest.database.genres.GenreDao
import com.example.movieinfotest.database.movie.MovieDao
import com.example.movieinfotest.database.moviedetails.MovieDetailsDao
import com.example.movieinfotest.network.responses.actors.Actor
import com.example.movieinfotest.network.responses.details.MovieDetails
import com.example.movieinfotest.network.responses.genre.Genre
import com.example.movieinfotest.network.responses.popular.Movie

@Database(
    entities = [Genre::class, Actor::class, Movie::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun actorDao():ActorDao
    abstract fun movieDao():MovieDao

}