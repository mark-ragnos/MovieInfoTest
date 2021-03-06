package com.example.movieinfotest.data.db.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieinfotest.data.entities.popular.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieList(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    fun loadMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clear(): Int

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieById(id: Int): Movie?
}
