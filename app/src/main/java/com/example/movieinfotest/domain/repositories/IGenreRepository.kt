package com.example.movieinfotest.domain.repositories

import com.example.movieinfotest.domain.entities.genre.Genre

interface IGenreRepository<T> {
    suspend fun getGenres(): List<Genre>?
}