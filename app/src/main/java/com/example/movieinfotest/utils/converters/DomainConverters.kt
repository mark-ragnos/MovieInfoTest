package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.actors.Crew
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.domain.entities.actor.CrewDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain as MovieDomain
import com.example.movieinfotest.domain.entities.actor.CastDomain as ActorDomain
import com.example.movieinfotest.data.entities.actors.Cast as ActorData
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
        posterPath,
        backdropPath
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

fun ActorDomain.toCastData(movieId: Int): ActorData {
    return ActorData(
        movieId, id, name, character, profilePath, gender
    )
}

fun List<ActorDomain>.toCastData(movieId: Int): List<ActorData> {
    return map {
        it.toCastData(movieId)
    }
}

fun CrewDomain.toCrewData(movieId: Int): Crew {
    return Crew(
        movieId,
        id,
        name,
        job,
        profilePath,
        gender
    )
}

fun List<CrewDomain>.toCrewData(movieId: Int): List<Crew> {
    return map {
        it.toCrewData(movieId)
    }
}
