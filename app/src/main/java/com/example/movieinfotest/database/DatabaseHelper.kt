package com.example.movieinfotest.database

import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.popular.Movie

class DatabaseHelper() {
    private val database: MovieDatabase = MovieApp.getInstance().getDatabase()

    fun getDatabase():MovieDatabase{
        return database
    }

    //Получение списка жанров
    suspend fun getAllGenres():List<Genre>{
        return database.genreDao().loadAll()
    }

    //Сохранение списка жанров
    suspend fun addAllGenres(genres: List<Genre>){
        database.genreDao().saveAll(genres)
    }

    //Сохранение в избранное

    //Сохранение в избранное списком


    suspend fun addMovieList(movies: List<Movie>){
        database.movieDao().saveMovieList(movies)
    }


}