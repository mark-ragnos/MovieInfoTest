package com.example.movieinfotest.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.movieinfotest.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

const val MALE = 2
const val FEMALE = 1

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

private fun loadImage(path: String?, x: Int, y: Int): RequestCreator {
    return Picasso.get()
        .load("https://www.themoviedb.org/t/p/w1280$path")
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

fun getDivider(context: Context, orientation: Int, drawableRes: Int): DividerItemDecoration {
    val divider = DividerItemDecoration(context, orientation)
    ContextCompat.getDrawable(context, drawableRes)?.let {
        divider.setDrawable(it)
    }
    return divider
}
