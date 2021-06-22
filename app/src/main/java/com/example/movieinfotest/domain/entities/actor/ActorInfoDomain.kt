package com.example.movieinfotest.domain.entities.actor

import androidx.annotation.IntRange

data class ActorInfoDomain(
    val birthday: String?,
    val knownForDepartment: String?,
    val deathDay: String?,
    val id: Int,
    val name: String,
    val alsoKnownAs: List<String>,
    @IntRange(from = 0, to = 3)
    val gender: Int,
    val biography: String,
    val popularity: Float,
    val placeOfBirth: String?,
    val profilePath: String?,
    val adult: Boolean,
    val homePage: String?
)
