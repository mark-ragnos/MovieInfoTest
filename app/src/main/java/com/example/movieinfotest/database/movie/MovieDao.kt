package com.example.movieinfotest.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieinfotest.network.responses.popular.Movie

@Dao
interface MovieDao {
    @Insert
    fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id:Int):Movie
}