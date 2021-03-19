package com.example.movieinfotest.data.entities.actors

import androidx.room.Entity


@Entity(primaryKeys = ["movie_id", "id"])
data class Actor(
    val movie_id:Int,
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String?
)