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

fun GenreDomain.toGenreDate(): GenreData {
    return GenreData(id, name)
}

fun List<GenreDomain>.toGenresData(): List<GenreData> {
    return map {
        GenreData(it.id, it.name)
    }
}

fun ActorDomain.toActorData(movieId: Int): ActorData {
    return ActorData(
        movieId, id, name, character, profilePath
    )
}

fun List<ActorDomain>.toActorData(movieId: Int): List<ActorData> {
    return map {
        ActorData(
            movieId, it.id, it.name, it.character, it.profilePath
        )
    }
}
