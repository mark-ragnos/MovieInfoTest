package com.example.movieinfotest.old


data class MovieFrom<T>(val movie: T, var isFromFavorite: Boolean) {
    companion object {
        const val FAVORITE = true
        const val REGULAR = false
    }
}
