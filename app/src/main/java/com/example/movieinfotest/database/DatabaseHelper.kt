package com.example.movieinfotest.database

import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.network.responses.genre.Genre
import com.example.movieinfotest.network.responses.popular.Movie

class DatabaseHelper() {
    private val database: MovieDatabase = MovieApp.getInstance().getDatabase()

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