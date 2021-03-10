package com.example.movieinfotest.network.responses.actors

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Actor(

    @PrimaryKey
    val id: Int,
    val gender: Int,
    val name: String,
    val character: String,
    val profile_path: String
)