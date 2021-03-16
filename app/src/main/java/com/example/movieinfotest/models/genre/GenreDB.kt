package com.example.movieinfotest.models.genre

import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "id"])
data class GenreDB(
    val movie_id: Int,
    val id: Int,
    val name: String
)
