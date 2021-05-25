package com.example.movieinfotest.domain.entities.actor

data class CastDomain(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String?,
    val gender: Int?
)
