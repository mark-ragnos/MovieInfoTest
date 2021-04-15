package com.example.movieinfotest.domain.usecases

import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.utils.network.NetworkStatus

class MovieInfoUseCase(
    private val movieRepository: IMovieRepository<Movie>
){

    suspend fun getMovieInfo(movie_id: Int, networkStatus: NetworkStatus): Movie? {
        if(networkStatus == NetworkStatus.OFFLINE)
            return movieRepository.getMovieInfoLocal(movie_id)
        return movieRepository.getMovieInfoRemote(movie_id)
    }
}