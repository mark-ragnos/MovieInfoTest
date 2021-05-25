package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.data.entities.popular.Movie
import com.example.movieinfotest.domain.entities.actor.CastDomain
import com.example.movieinfotest.data.entities.actors.Cast
import com.example.movieinfotest.data.entities.actors.Crew
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.genre.GenreMovieDB
import com.example.movieinfotest.domain.entities.actor.CrewDomain

fun Movie.toMovieDomain(
    genres: List<GenreDomain>? = null,
    cast: List<CastDomain>? = null,
    crew: List<CrewDomain>? = null
): MovieDomain {

    return MovieDomain(
        id,
        title,
        voteAverage,
        releaseDate,
        posterPath,
        overview,
        genres = genres,
        casts = cast,
        crews = crew
    )
}

fun MovieDetails.toMovieDomain(
    cast: List<CastDomain>? = null,
    crew: List<CrewDomain>? = null
): MovieDomain {
    return MovieDomain(
        id,
        title,
        voteAverage,
        releaseDate,
        posterPath,
        overview,
        genres?.toGenreDomain(),
        casts = cast,
        crews = crew
    )
}

fun Cast.toCastDomain(): CastDomain {
    return CastDomain(
        id,
        name,
        character,
        profilePath,
        gender
    )
}

fun List<Cast>.toCastDomain(): List<CastDomain> {
    return map {
        it.toCastDomain()
    }
}

fun Genre.toGenreDomain(): GenreDomain {
    return GenreDomain(id, name)
}

fun List<Genre>.toGenreDomain(): List<GenreDomain> {
    return map {
        it.toGenreDomain()
    }
}

fun Genre.toGenreDB(movieId: Int): GenreMovieDB {
    return GenreMovieDB(movieId, id, name)
}

fun List<Genre>.toGenreDB(movieId: Int): List<GenreMovieDB> {
    return map {
        it.toGenreDB(movieId)
    }
}

fun Crew.toCrewDomain(): CrewDomain {
    return CrewDomain(id, name, job, profilePath, gender)
}

fun List<Crew>.toCrewDomain(): List<CrewDomain> {
    return map {
        it.toCrewDomain()
    }
}
