package com.example.movieinfotest.utils

import Genre
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlin.text.StringBuilder

fun String.getYear():String{
    return this.substring(0,4)
}


fun getGenreList(list: List<Genre>):String{
    var result = StringBuilder()
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
fun ImageView.registerImage(path: String, x:Int=100, y:Int=150){
    Picasso.get()
        .load("https://www.themoviedb.org/t/p/w1280${path}")
        .resize(x, y)
        .centerCrop()
        .into(this)
}


