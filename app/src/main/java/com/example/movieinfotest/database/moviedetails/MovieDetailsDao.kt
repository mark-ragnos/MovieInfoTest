package com.example.movieinfotest.database.moviedetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieinfotest.models.details.MovieDetails


@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie: MovieDetails)

    @Query("SELECT * FROM moviedetails WHERE id = :id")
    fun getMovieByID(id: Int):MovieDetails
}