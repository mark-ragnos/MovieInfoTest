package com.example.movieinfotest.database.genres

import Genre
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(genre: Genre)

    @Query("SELECT * FROM genres")
    fun loadList():List<Genre>

}