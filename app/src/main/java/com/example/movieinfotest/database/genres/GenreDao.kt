package com.example.movieinfotest.database.genres

import Genre
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAll(genres: List<Genre>)

    @Query("SELECT * FROM genre")
    fun loadAll():List<Genre>

}