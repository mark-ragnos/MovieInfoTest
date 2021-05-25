package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.db.FavoriteMovie
import com.example.movieinfotest.domain.entities.movie.MovieDomain

fun FavoriteMovie.toMovieDomain(): MovieDomain {
    return MovieDomain(
        movie.id,
        movie.title,
        movie.voteAverage,
        movie.releaseDate,
        movie.posterPath,
        movie.overview,
        genres.toGenreDomain(),
        casts.toCastDomain(),
        crew.toCrewDomain()
    )
}
