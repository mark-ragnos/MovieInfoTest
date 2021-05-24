package com.example.movieinfotest.data.entities.genre

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "id"])
data class GenreDB(
    val movieId: Int,
    val id: Int,
    val name: String
)
