package com.example.movieinfotest.data.db

import androidx.paging.PagingSource
import com.example.movieinfotest.data.entities.actors.Actor
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.popular.Movie
import com.example.movieinfotest.utils.toGenre
import com.example.movieinfotest.utils.toMovieDetails

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

    suspend fun saveInFavorite(movieDetails: MovieDetails, actors: List<Actor>?) {
        database.favoriteDao().saveInFavorite(movieDetails, actors)
    }

    fun getGetFavoriteList(): PagingSource<Int, MovieDetailsDB> {
        return database.favoriteDao().getFavoriteList()
    }

    suspend fun getDetailsFromFavorite(id: Int): MovieDetails? {
        val movieDB = database.favoriteDao().getFavoriteById(id)
        val genres = database.favoriteDao().getGenres(id).map {
            it.toGenre()
        }
        return movieDB?.toMovieDetails(genres)
    }

    suspend fun getDetailsById(id: Int): MovieDetails? {
        return getFromList(id)
    }

    private suspend fun getFromList(id: Int): MovieDetails? {
        return database.movieDao().getMovieById(id)?.toMovieDetails()
    }

    suspend fun getActorsById(id: Int): List<Actor> {
        return database.favoriteDao().getActors(id)
    }

    suspend fun removeFromFavorite(id: Int) {
        database.favoriteDao().removeFromFavorite(id)
    }

    fun loadMovies(): PagingSource<Int, Movie> {
        return database.movieDao().loadMovies()
    }
}
