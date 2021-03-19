package com.example.movieinfotest.domain.repositories

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository<T : Any> {

    suspend fun getMovies(): Flow<PagingData<T>>

    suspend fun getRandomMovie(genre: Int, year: Int): T

    suspend fun getMovieInfoLocal(movie_id: Int): Movie?

    suspend fun getMovieInfoRemote(movie_id: Int): Movie?
}