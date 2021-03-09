package com.example.movieinfotest.network.responses.popular


import com.google.gson.annotations.SerializedName


data class Results(

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("poster_path") val poster_path: String
)