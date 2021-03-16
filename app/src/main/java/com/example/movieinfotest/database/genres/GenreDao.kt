package com.example.movieinfotest.database.genres

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieinfotest.models.genre.Genre


@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(genres: List<Genre>)

    @Query("SELECT * FROM genre")
    suspend fun loadAll(): List<Genre>?

}