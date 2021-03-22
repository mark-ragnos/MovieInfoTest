package com.example.movieinfotest.domain.repositories

import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.utils.DataSourceMode

interface IGenreRepository<T> {
    suspend fun getGenres(dataSourceMode: DataSourceMode): List<Genre>?
}