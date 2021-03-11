package com.example.movieinfotest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieinfotest.database.actors.ActorDao
import com.example.movieinfotest.database.genres.GenreDao
import com.example.movieinfotest.database.movie.MovieDao
import com.example.movieinfotest.database.movie.RemoteKeys
import com.example.movieinfotest.database.movie.RemoteKeysDao
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.popular.Movie

@Database(
    entities = [Genre::class, Actor::class, Movie::class, RemoteKeys::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun actorDao(): ActorDao
    abstract fun movieDao(): MovieDao
    abstract fun remoteDao():RemoteKeysDao

}