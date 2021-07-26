package com.example.movieinfotest.utils

import android.widget.ImageView
import com.example.movieinfotest.R
import com.example.movieinfotest.utils.moviedbSpecificUtils.FEMALE
import com.example.movieinfotest.utils.moviedbSpecificUtils.MALE
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

const val FLAG_POSTER_W154 = "w154"
const val FLAG_POSTER_W185 = "w185"

const val FLAG_BACKDROP_W300 = "w300"
const val FLAG_BACKDROP_W780 = "w780"

const val FLAG_PROFILE_W154 = "w154"
const val FLAG_PROFILE_W185 = "w185"
const val FLAG_PROFILE_FACE = "w240_and_h266_face"

/**
 * Соотношение постеров у = х * 1.5
 */
fun ImageView.displayMoviePoster(path: String?, x: Int = 100, y: Int = (x * 1.5).toInt()) {
    loadImage(path, x, y)
        .moviePlaceholder()
        .into(this)
}

/**
 * Соотношение постеров у = х * 1.5
 */
fun ImageView.displayActorPicture(
    path: String?,
    gender: Int? = null,
    x: Int = 100,
    y: Int = (x * 1.5).toInt()
) {
    loadImage(path, x, y)
        .actorPlaceholder(gender ?: MALE)
        .into(this)
}

fun ImageView.displayBackdrop(
    path: String?
) {
    Picasso.get()
        .load("https://www.themoviedb.org/t/p/$FLAG_BACKDROP_W300$path")
        .into(this)
}

private fun loadImage(
    path: String?,
    x: Int,
    y: Int,
    weightFlag: String = FLAG_POSTER_W154
): RequestCreator {
    return Picasso.get()
        .load("https://www.themoviedb.org/t/p/$weightFlag$path")
        .resize(x, y)
        .centerCrop()
}

private fun RequestCreator.moviePlaceholder(): RequestCreator {
    return this.setPlaceholders(R.drawable.ic_placeholder_movie)
}

private fun RequestCreator.actorPlaceholder(gender: Int): RequestCreator {
    if (gender == FEMALE) {
        return this.setPlaceholders(R.drawable.ic_placeholder_female)
    }

    return this.setPlaceholders(R.drawable.ic_placeholder_male)
}

private fun RequestCreator.setPlaceholders(placeholderResId: Int): RequestCreator {
    return this.placeholder(placeholderResId)
        .error(placeholderResId)
}
