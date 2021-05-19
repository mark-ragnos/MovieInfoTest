package com.example.movieinfotest.domain.usecases

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.Flow

class PopularMovieUseCase(
    private val movieRepository: IMovieRepository<MovieDomain>
) {

    fun getPopularList(): Flow<PagingData<MovieDomain>>{
        return movieRepository.getMovies()
    }
}