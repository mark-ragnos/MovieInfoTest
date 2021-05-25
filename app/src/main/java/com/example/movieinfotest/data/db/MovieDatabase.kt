package com.example.movieinfotest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieinfotest.data.db.favorite.FavoriteDao
import com.example.movieinfotest.data.db.genres.GenreDao
import com.example.movieinfotest.data.db.movie.MovieDao
import com.example.movieinfotest.data.entities.remotekeys.RemoteKeys
import com.example.movieinfotest.data.db.remotekeys.RemoteKeysDao
import com.example.movieinfotest.data.entities.actors.Cast
import com.example.movieinfotest.data.entities.actors.Crew
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.GenreMovieDB
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.popular.Movie

@Database(
    entities = [
        Genre::class,
        Movie::class,
        RemoteKeys::class,
        Cast::class,
        GenreMovieDB::class,
        MovieDetailsDB::class,
        Crew::class
    ],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun movieDao(): MovieDao
    abstract fun remoteDao(): RemoteKeysDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        fun create(context: Context): MovieDatabase {
            return Room.databaseBuilder(context, MovieDatabase::class.java, "db_movie.db").build()
        }
    }
}
