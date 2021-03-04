package com.example.movieinfotest.network.response_classes.actors

import com.google.gson.annotations.SerializedName

data class FilmActors(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: Actors
)