package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.genre.GenreMovieDB
import com.example.movieinfotest.domain.entities.genre.GenreDomain

fun GenreMovieDB.toGenreDomain(): GenreDomain {
    return GenreDomain(
        id, name
    )
}

fun List<GenreMovieDB>.toGenreDomain(): List<GenreDomain> {
    return map {
        it.toGenreDomain()
    }
}
