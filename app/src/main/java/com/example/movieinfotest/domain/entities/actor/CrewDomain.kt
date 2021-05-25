package com.example.movieinfotest.domain.entities.actor

data class CrewDomain(
    val id: Int,
    val name: String,
    val job: String,
    val profilePath: String?,
    val gender: Int?
)
