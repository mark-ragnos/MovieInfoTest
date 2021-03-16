package com.example.movieinfotest.models.genre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreDB(
    val movie_id: Int,
    val id: Int,
    val name: String,

    @PrimaryKey(autoGenerate = true)
    val db_id:Int?
)
