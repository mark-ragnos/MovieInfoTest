package com.example.movieinfotest.network.responses.popular



data class PopularFilms(

    val page: Int,
    val results: List<Movie>,
    val total_pages: Int
)