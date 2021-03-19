package com.example.movieinfotest.domain.entities.movie

import com.example.movieinfotest.domain.entities.actor.Actor
import com.example.movieinfotest.domain.entities.genre.Genre

data class Movie(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val release_date: String?,
    val poster_path: String?,
    val overview:String,
    val genres: List<Genre>?,
    val actors: List<Actor>?
)
