package com.example.movieinfotest.data.entities.details

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieinfotest.data.entities.genre.Genre
import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Int,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    val genres: List<Genre>?,
    @SerializedName("poster_path")
    val posterPath: String?
)

@Entity
data class MovieDetailsDB(
    @PrimaryKey
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String?,
    val posterPath: String?,
    var addDate: Long?
)
