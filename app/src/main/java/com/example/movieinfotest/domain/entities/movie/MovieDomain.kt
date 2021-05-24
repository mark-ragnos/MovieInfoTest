package com.example.movieinfotest.domain.entities.movie

import com.example.movieinfotest.domain.entities.actor.ActorDomain
import com.example.movieinfotest.domain.entities.genre.GenreDomain

data class MovieDomain(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String?,
    val posterPath: String?,
    val overview: String,
    val genres: List<GenreDomain>?,
    val actors: List<ActorDomain>?
)
