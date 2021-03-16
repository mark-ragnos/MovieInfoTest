package com.example.movieinfotest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieinfotest.database.favorite.FavoriteDao
import com.example.movieinfotest.database.genres.GenreDao
import com.example.movieinfotest.database.movie.MovieDao
import com.example.movieinfotest.database.movie.RemoteKeys
import com.example.movieinfotest.database.movie.RemoteKeysDao
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.models.genre.GenreDB
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.popular.Movie

@Database(
    entities = [Genre::class, Movie::class, RemoteKeys::class, Actor::class, GenreDB::class, MovieDetailsDB::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun movieDao(): MovieDao
    abstract fun remoteDao(): RemoteKeysDao
    abstract fun favoriteDao(): FavoriteDao
}