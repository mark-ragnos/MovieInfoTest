package com.example.movieinfotest.network.responses.actors

import com.google.gson.annotations.SerializedName

data class FilmActors(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<Actor>
)