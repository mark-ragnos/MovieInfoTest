package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.domain.entities.movie.MovieDomain as MovieDomain
import com.example.movieinfotest.data.entities.popular.Movie as MovieData
import com.example.movieinfotest.domain.entities.actor.ActorDomain as ActorDomain
import com.example.movieinfotest.data.entities.actors.Actor as ActorData
import com.example.movieinfotest.domain.entities.genre.GenreDomain as GenreDomain
import com.example.movieinfotest.data.entities.genre.Genre as GenreData

fun MovieData.toMovieDomain(
    genres: List<GenreDomain>? = null,
    actors: List<ActorDomain>? = null
): MovieDomain {

    return MovieDomain(
        id,
        title,
        voteAverage,
        releaseDate,
        posterPath,
        overview,
        genres = genres,
        actors = actors
    )
}

fun MovieDetails.toMovieDomain(actors: List<ActorDomain>? = null): MovieDomain {
    return MovieDomain(
        id,
        title,
        voteAverage,
        releaseDate,
        posterPath,
        overview,
        genres?.toGenreDomain(),
        actors = actors
    )
}

fun ActorData.toActorDomain(): ActorDomain {
    return ActorDomain(
        id,
        name,
        character,
        profilePath
    )
}

fun List<ActorData>.toActorDomain(): List<ActorDomain> {
    return map {
        ActorDomain(
            it.id,
            it.name,
            it.character,
            it.profilePath
        )
    }
}

fun GenreData.toGenreDomain(): GenreDomain {
    return GenreDomain(id, name)
}

fun List<GenreData>.toGenreDomain(): List<GenreDomain> {
    return map {
        GenreDomain(it.id, it.name)
    }
}
