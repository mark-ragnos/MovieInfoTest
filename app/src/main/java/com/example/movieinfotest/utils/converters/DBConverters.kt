package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.domain.entities.movie.MovieDomain

fun MovieDetailsDB.toMovieDomain(): MovieDomain {
    return MovieDomain(
        id,
        title,
        voteAverage,
        releaseDate,
        posterPath,
        overview,
        null,
        null
    )
}
