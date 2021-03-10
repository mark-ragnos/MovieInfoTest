package com.example.movieinfotest.network.responses.popular


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val vote_average: Double,
    val release_date: String,
    val poster_path: String
)