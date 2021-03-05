package com.example.movieinfotest.database.genres

import Genres
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(genres: Genres)

    @Query("SELECT * FROM genres")
    fun loadList():List<Genres>

}