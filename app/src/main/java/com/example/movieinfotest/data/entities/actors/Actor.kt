package com.example.movieinfotest.data.entities.actors

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["movieId", "id"])
data class Actor(
    @SerializedName("movie_id")
    val movieId: Int,
    val id: Int,
    val name: String,
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String?
)
