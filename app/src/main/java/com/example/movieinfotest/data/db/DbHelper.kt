package com.example.movieinfotest.data.db

import androidx.paging.PagingSource
import com.example.movieinfotest.data.entities.actors.Cast
import com.example.movieinfotest.data.entities.actors.Crew
import com.example.movieinfotest.data.entities.db.FavoriteMovie
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.popular.Movie

class DbHelper(
    private val database: MovieDatabase
) {

    fun getDatabase(): MovieDatabase {
        return database
    }

    suspend fun getAllGenres(): List<Genre>? {
        return database.genreDao().loadAll()
    }

    suspend fun addAllGenres(genres: List<Genre>) {
        database.genreDao().saveAll(genres)
    }

    suspend fun saveInFavorite(movieDetails: MovieDetails, casts: List<Cast>?, crews: List<Crew>?) {
        database.favoriteDao().saveInFavorite(movieDetails, casts, crews)
    }

    fun getGetFavoriteList(): PagingSource<Int, MovieDetailsDB> {
        return database.favoriteDao().getFavoriteList()
    }

    suspend fun getDetailsFromFavorite(id: Int): FavoriteMovie? {
        return database.favoriteDao().getFavoriteById(id)
    }

    suspend fun removeFromFavorite(id: Int) {
        database.favoriteDao().removeFromFavorite(id)
    }

    fun loadMovies(): PagingSource<Int, Movie> {
        return database.movieDao().loadMovies()
    }
}
