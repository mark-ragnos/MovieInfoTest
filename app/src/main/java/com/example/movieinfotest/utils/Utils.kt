package com.example.movieinfotest.utils

import Genres
import kotlin.text.StringBuilder

fun String.getYear():String{
    return this.substring(0,4)
}


fun getGenreList(list: List<Genres>):String{
    var result = StringBuilder()
    list.forEach {
        result.append(it.name)
        result.append(", ")
    }
    result.deleteCharAt(result.lastIndex)
    result.deleteCharAt(result.lastIndex)

    return result.toString()
}


