package com.example.movieinfotest.domain.repositories

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.Flow

interface IMovieRepository<T : Any> {

    fun getMovies(): Flow<PagingData<T>>

    suspend fun getRandomMovie(genre: String, year: String): T

    suspend fun getMovieInfo(movieId: Int, networkStatus: NetworkConnection.STATUS): MovieDomain?
}
