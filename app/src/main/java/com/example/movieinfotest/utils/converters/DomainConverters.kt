package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.domain.entities.movie.MovieDomain as MovieDomain
import com.example.movieinfotest.domain.entities.actor.ActorDomain as ActorDomain
import com.example.movieinfotest.data.entities.actors.Actor as ActorData
import com.example.movieinfotest.domain.entities.genre.GenreDomain as GenreDomain
import com.example.movieinfotest.data.entities.genre.Genre as GenreData

fun MovieDomain.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id,
        title,
        voteAverage,
        overview,
        releaseDate,
        genres?.toGenresData(),
        posterPath
    )
}

fun GenreDomain.toGenreData(): GenreData {
    return GenreData(id, name)
}

fun List<GenreDomain>.toGenresData(): List<GenreData> {
    return map {
        it.toGenreData()
    }
}

fun ActorDomain.toActorData(movieId: Int): ActorData {
    return ActorData(
        movieId, id, name, character, profilePath, gender
    )
}

fun List<ActorDomain>.toActorData(movieId: Int): List<ActorData> {
    return map {
        it.toActorData(movieId)
    }
}
