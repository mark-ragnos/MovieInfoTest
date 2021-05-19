package com.example.movieinfotest.domain.usecases

import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.utils.network.NetworkConnection

class MovieInfoUseCase(
    private val movieRepository: IMovieRepository<MovieDomain>
) {
    suspend fun getMovieInfo(movie_id: Int, networkStatus: NetworkConnection.STATUS): MovieDomain? {
        return movieRepository.getMovieInfo(movie_id, networkStatus)
    }
}
