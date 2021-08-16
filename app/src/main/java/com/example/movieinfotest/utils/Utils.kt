package com.example.movieinfotest.utils

import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB

fun String?.getYear(): String {
    if (this == null || length < 4) {
        return "Unknown Year"
    }

    return this.substring(0, 4)
}

fun MovieDetails.toMovieDetailsDB(): MovieDetailsDB {
    return MovieDetailsDB(
        id,
        title,
        voteAverage,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        null
    )
}
