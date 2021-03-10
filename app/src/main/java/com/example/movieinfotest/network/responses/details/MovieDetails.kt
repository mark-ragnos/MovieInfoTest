package com.example.movieinfotest.network.responses.details


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.movieinfotest.network.responses.genre.Genre

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