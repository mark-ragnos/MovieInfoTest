package com.example.movieinfotest.models.details


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieinfotest.models.genre.Genre

@Entity
data class MovieDetails(

    @PrimaryKey
    val id: Int,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String,
    val genres: List<Genre>,
    val poster_path: String
)