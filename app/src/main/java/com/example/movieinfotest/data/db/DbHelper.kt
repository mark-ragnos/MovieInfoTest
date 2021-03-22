package com.example.movieinfotest.data.db

import androidx.paging.PagingSource
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.data.entities.actors.Actor
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.utils.toGenre
import com.example.movieinfotest.utils.toMovieDetails

class DbHelper(
    private val database: MovieDatabase
) {

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

    //Загрузка из избранного по ИД
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
}