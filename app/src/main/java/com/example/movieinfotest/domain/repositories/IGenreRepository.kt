package com.example.movieinfotest.domain.repositories

import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.utils.network.NetworkConnection

interface IGenreRepository<T> {
    suspend fun getGenres(networkStatus: NetworkConnection.STATUS): List<Genre>?
}
