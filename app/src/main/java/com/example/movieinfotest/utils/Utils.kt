package com.example.movieinfotest.utils

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.example.movieinfotest.R
import com.example.movieinfotest.models.genre.Genre
import com.squareup.picasso.Picasso
import kotlin.text.StringBuilder

fun String?.getYear():String{
    if(this == null)
        return "Unknown Year"
    if(this.length<4)
        return "Unknown Year"

    return this.substring(0,4)
}


fun getGenreList(list: List<Genre>?):String{
    if(list?.size==0||(list == null))
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
fun ImageView.registerImage(path: String?, x:Int=100, y:Int=150){
    if (path == null)
        return
    Picasso.get()
        .load("https://www.themoviedb.org/t/p/w1280${path}")
        .resize(x, y)
        .centerCrop()
        .into(this)
}