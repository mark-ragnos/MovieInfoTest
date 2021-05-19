package com.example.movieinfotest.domain.usecases

import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.utils.network.NetworkConnection

class RandomMovieUseCase(
    private val genreRepository: IGenreRepository<GenreDomain>,
    private val movieRepository: IMovieRepository<MovieDomain>
) {

    suspend fun getGenres(networkStatus: NetworkConnection.STATUS): List<GenreDomain>? {
        return genreRepository.getGenres(networkStatus)
    }

    suspend fun getRandomMovie(genre: String, year: String): MovieDomain{
        return movieRepository.getRandomMovie(genre, year)
    }
}
