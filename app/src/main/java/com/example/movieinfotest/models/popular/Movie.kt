package com.example.movieinfotest.models.popular


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val vote_average: Double,
    val release_date: String?,
    val poster_path: String?,
    val popularity: Double
)