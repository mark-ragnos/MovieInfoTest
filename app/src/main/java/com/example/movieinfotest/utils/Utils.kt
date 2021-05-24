package com.example.movieinfotest.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.genre.GenreDB
import com.example.movieinfotest.data.entities.popular.Movie

fun String?.getYear(): String {
    if (this == null || length < 4) {
        return "Unknown Year"
    }

    return this.substring(0, 4)
}

fun getGenreList(list: List<com.example.movieinfotest.domain.entities.genre.GenreDomain>?): String {
    if (list.isNullOrEmpty()) {
        return "Don't find any genres"
    }
    val result = StringBuilder()
    list.forEach {
        result.append(it.name)
        result.append(", ")
    }
    result.deleteCharAt(result.lastIndex)
    result.deleteCharAt(result.lastIndex)

    return result.toString()
}

fun MovieDetails.toMovieDetailsDB(): MovieDetailsDB {
    return MovieDetailsDB(id, title, voteAverage, overview, releaseDate, posterPath, null)
}

fun MovieDetailsDB.toMovieDetails(genres: List<Genre>?): MovieDetails {
    return MovieDetails(
        id,
        title,
        voteAverage,
        overview,
        releaseDate,
        genres = genres,
        posterPath
    )
}

fun Genre.toGenreDB(movieId: Int): GenreDB {
    return GenreDB(movieId, id, name)
}

fun GenreDB.toGenre(): Genre {
    return Genre(id, name)
}

fun Movie.toMovieDetails(): MovieDetails {
    return MovieDetails(id, title, voteAverage, overview, releaseDate, null, posterPath)
}

fun Context.isDarkThemeOn(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
}
