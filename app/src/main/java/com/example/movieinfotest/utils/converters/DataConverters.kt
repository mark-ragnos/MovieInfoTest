package com.example.movieinfotest.utils

import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.domain.entities.movie.Movie as MovieDomain
import com.example.movieinfotest.data.entities.popular.Movie as MovieData
import com.example.movieinfotest.domain.entities.actor.Actor as ActorDomain
import com.example.movieinfotest.data.entities.actors.Actor as ActorData
import com.example.movieinfotest.domain.entities.genre.Genre as GenreDomain
import com.example.movieinfotest.data.entities.genre.Genre as GenreData


fun MovieData.toMovieDomain(
    genres: List<GenreDomain>? = null,
    actors: List<ActorDomain>? = null
): MovieDomain {

    return MovieDomain(
        id,
        title,
        vote_average,
        release_date,
        poster_path,
        overview,
        genres = genres,
        actors = actors
    )
}

fun MovieDetails.toMovieDomain(actors: List<ActorDomain>? = null): MovieDomain {
    return MovieDomain(
        id,
        title,
        vote_average,
        release_date,
        poster_path,
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
        profile_path
    )
}

fun List<ActorData>.toActorDomain(): List<ActorDomain> {
    val actors = map {
        ActorDomain(
            it.id,
            it.name,
            it.character,
            it.profile_path
        )
    }
    return actors
}


fun GenreData.toGenreDomain(): GenreDomain {
    return GenreDomain(id, name)
}

fun List<GenreData>.toGenreDomain(): List<GenreDomain> {
    val genres = map {
        GenreDomain(it.id, it.name)
    }
    return genres
}