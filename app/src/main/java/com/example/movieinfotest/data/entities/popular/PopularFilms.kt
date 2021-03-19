package com.example.movieinfotest.data.entities.popular



data class PopularFilms(

    val page: Int,
    val results: List<Movie>,
    val total_pages: Int
)