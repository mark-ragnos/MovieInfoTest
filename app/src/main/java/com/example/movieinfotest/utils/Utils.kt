package com.example.movieinfotest.utils

import android.widget.ImageView
import com.example.movieinfotest.R
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.genre.GenreDB
import com.example.movieinfotest.data.entities.popular.Movie
import com.squareup.picasso.Picasso

fun String?.getYear(): String {
    if (this == null)
        return "Unknown Year"
    if (this.length < 4)
        return "Unknown Year"

    return this.substring(0, 4)
}


fun getGenreList(list: List<com.example.movieinfotest.domain.entities.genre.Genre>?): String {
    if (list?.size == 0 || (list == null))
        return "Don't find any genres"
    val result = StringBuilder()
    list.forEach {
        result.append(it.name)
        result.append(", ")
    }
    result.deleteCharAt(result.lastIndex)
    result.deleteCharAt(result.lastIndex)

    return result.toString()
}

/**
 * Соотношение постеров у = х * 1.5
 */
fun ImageView.registerImage(path: String?, x: Int = 100, y: Int = 150) {
    Picasso.get()
        .load("https://www.themoviedb.org/t/p/w1280${path}")
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .resize(x, y)
        .centerCrop()
        .into(this)
}

fun MovieDetails.toMovieDetailsDB(): MovieDetailsDB {
    return MovieDetailsDB(id, title, vote_average, overview, release_date, poster_path, null)
}

fun MovieDetailsDB.toMovieDetails(genres: List<Genre>?): MovieDetails {
    return MovieDetails(
        id,
        title,
        vote_average,
        overview,
        release_date,
        genres = genres,
        poster_path
    )
}

fun Genre.toGenreDB(movie_id: Int): GenreDB {
    return GenreDB(movie_id, id, name)
}

fun GenreDB.toGenre(): Genre {
    return Genre(id, name)
}

fun Movie.toMovieDetails(): MovieDetails {
    return MovieDetails(id, title, vote_average, overview, release_date, null, poster_path)
}

fun isCorrectUserData(email: String, password: String):Boolean{

    if (password.length < 8 || email == "")
        return false

    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}