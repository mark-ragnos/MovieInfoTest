package com.example.movieinfotest.data.entities.api.actor

import androidx.annotation.IntRange
import com.google.gson.annotations.SerializedName

data class ActorInfo(
    val birthday: String?,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("deathday")
    val deathDay: String?,
    val id: Int,
    val name: String,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,
    @IntRange(from = 0, to = 3)
    val gender: Int,
    val biography: String,
    val popularity: Float,
    val placeOfBirth: String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    val adult: Boolean,
    @SerializedName("homepage")
    val homePage: String?
)
