package com.example.movieinfotest.data.entities.actors

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["movieId", "id"])
data class Crew(
    @SerializedName("movie_id")
    val movieId: Int,
    val id: Int,
    val name: String,
    val job: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val gender: Int?
)
