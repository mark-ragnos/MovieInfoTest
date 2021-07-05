package com.example.movieinfotest.domain.usecases

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.Flow

class MovieUseCase(
    private val movieRepository: IMovieRepository
) {
    fun getPopularMovies(): Flow<PagingData<MovieDomain>> {
        return movieRepository.getMovies()
    }

    suspend fun getMovieInfo(movieId: Int, networkStatus: NetworkConnection.STATUS): MovieDomain? {
        return movieRepository.getMovieInfo(movieId, networkStatus)
    }

    suspend fun getRandomMovie(genre: String, year: String): MovieDomain {
        return movieRepository.getRandomMovie(genre, year)
    }
}
