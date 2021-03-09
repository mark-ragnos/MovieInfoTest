package com.example.movieinfotest.network.responses.details


import com.example.movieinfotest.network.responses.details.Genre
import com.google.gson.annotations.SerializedName


data class MovieDetails(


    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("genres") val genres: List<Genre>,
    val poster_path: String
)