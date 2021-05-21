package com.example.movieinfotest.domain.usecases

import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.utils.network.NetworkConnection

class GenreUseCase(
    private val genreRepository: IGenreRepository<GenreDomain>
) {
    suspend fun getAllGenres(networkStatus: NetworkConnection.STATUS): List<GenreDomain>? {
        return genreRepository.getGenres(networkStatus)
    }
}
