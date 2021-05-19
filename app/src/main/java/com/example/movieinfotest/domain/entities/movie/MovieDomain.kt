package com.example.movieinfotest.domain.entities.movie

import com.example.movieinfotest.domain.entities.actor.ActorDomain
import com.example.movieinfotest.domain.entities.genre.GenreDomain

data class MovieDomain(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val release_date: String?,
    val poster_path: String?,
    val overview: String,
    val genres: List<GenreDomain>?,
    val actors: List<ActorDomain>?
)
