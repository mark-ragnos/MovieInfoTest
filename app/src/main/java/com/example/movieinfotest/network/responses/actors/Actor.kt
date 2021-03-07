package com.example.movieinfotest.network.responses.actors

data class Actor(
    val id: Int,
    val gender: Int,
    val name: String,
    val character: String,
    val profile_path: String
)