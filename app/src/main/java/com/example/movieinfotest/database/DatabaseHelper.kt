package com.example.movieinfotest.database

import android.util.Log
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.utils.toGenre
import com.example.movieinfotest.utils.toMovieDetails

class DatabaseHelper() {
    private val database: MovieDatabase = MovieApp.getInstance().getDatabase()

    fun getDatabase(): MovieDatabase {
        return database
    }

    //Получение списка жанров
    suspend fun getAllGenres(): List<Genre>? {
        return database.genreDao().loadAll()
    }

    //Сохранение списка жанров
    suspend fun addAllGenres(genres: List<Genre>) {
        database.genreDao().saveAll(genres)
    }

    //Сохранение в избранное
    suspend fun saveInFavorite(movieDetails: MovieDetails, actors: List<Actor>?) {
        database.favoriteDao().saveInFavorite(movieDetails, actors)
    }

    //Загрузка из избранного списка
    suspend fun getGetFavoriteList(): List<MovieDetailsDB>? {
        val movies = database.favoriteDao().getFavoriteList()
        return movies
    }

    suspend fun getDetailsFromFavorite(id: Int): MovieDetails? {
        val movieDB = database.favoriteDao().getFavoriteById(id)
        val genres = database.favoriteDao().getGenres(id).map {
            it.toGenre()
        }
        return movieDB?.toMovieDetails(genres)
    }

    //Загрузка из избранного по ИД
    suspend fun getDetailsById(id: Int): MovieDetails? {
        val movie = getFromList(id)
        return movie
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
}