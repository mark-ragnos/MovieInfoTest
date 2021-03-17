package com.example.movieinfotest.utils

import com.example.movieinfotest.models.details.MovieDetails

data class MovieFrom(
    val movie: MovieDetails?,
    var isFromFavorite: Boolean
){
    companion object{
        const val FAVORITE = true
        const val REGULAR = false
    }
}
