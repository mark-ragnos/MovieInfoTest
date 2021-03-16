package com.example.movieinfotest.models.actors

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Actor(
    val movie_id:Int,
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String?,

    @PrimaryKey(autoGenerate = true)
    val db_id: Int?
)