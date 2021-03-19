package com.example.movieinfotest.data.entities.details


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieinfotest.data.entities.genre.Genre

data class MovieDetails(

    val id: Int,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String?,
    val genres: List<Genre>?,
    val poster_path: String?
)

@Entity
data class MovieDetailsDB(
    @PrimaryKey
    val id: Int,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String?,
    val poster_path: String?
)