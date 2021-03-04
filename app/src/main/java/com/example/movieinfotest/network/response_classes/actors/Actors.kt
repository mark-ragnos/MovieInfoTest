package com.example.movieinfotest.network.response_classes.actors

data class Actors(
    val id: Int,
    val gender: Int,
    val name: String,
    val character: String,
    val profile_path: String
)