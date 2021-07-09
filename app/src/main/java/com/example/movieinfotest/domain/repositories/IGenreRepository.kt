package com.example.movieinfotest.domain.repositories

import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.utils.network.NetworkConnection

interface IGenreRepository {
    suspend fun getGenres(networkStatus: NetworkConnection.STATUS): List<GenreDomain>?
}
