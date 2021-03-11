package com.example.movieinfotest.models.popular



data class PopularFilms(

    val page: Int,
    val results: List<Movie>,
    val total_pages: Int
)