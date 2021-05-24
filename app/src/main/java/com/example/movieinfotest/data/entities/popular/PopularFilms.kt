package com.example.movieinfotest.data.entities.popular

import com.google.gson.annotations.SerializedName

data class PopularFilms(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int
)
