package com.example.movieinfotest.database.genres

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieinfotest.network.responses.genre.Genre


@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAll(genres: List<Genre>)

    @Query("SELECT * FROM genre")
    fun loadAll():List<Genre>

}