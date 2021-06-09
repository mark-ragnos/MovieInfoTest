package com.example.movieinfotest.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.Genre
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

fun Movie.toMovieDetails(): MovieDetails {
    return MovieDetails(id, title, voteAverage, overview, releaseDate, null, posterPath)
}

fun Context.isDarkThemeOn(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun FragmentManager.reRunFragment(fragment: Fragment) {
    beginTransaction().detach(fragment).commit()
    beginTransaction().attach(fragment).commit()
}
