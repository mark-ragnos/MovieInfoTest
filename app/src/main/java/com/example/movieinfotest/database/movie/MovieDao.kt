package com.example.movieinfotest.database.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieinfotest.models.popular.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieList(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    fun loadMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clear(): Int

}