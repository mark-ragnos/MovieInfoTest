package com.example.movieinfotest.utils


data class MovieFrom<T>(val movie: T, var isFromFavorite: Boolean) {
    companion object {
        const val FAVORITE = true
        const val REGULAR = false
    }
}
