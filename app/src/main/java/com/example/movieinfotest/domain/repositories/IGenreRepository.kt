package com.example.movieinfotest.domain.repositories

import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.utils.network.NetworkStatus

interface IGenreRepository<T> {
    suspend fun getGenres(networkStatus: NetworkStatus): List<Genre>?
}