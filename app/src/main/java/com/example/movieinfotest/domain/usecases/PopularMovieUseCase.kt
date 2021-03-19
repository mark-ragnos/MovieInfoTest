package com.example.movieinfotest.domain.usecases

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.Flow

class PopularMovieUseCase(
    private val movieRepository: IMovieRepository<Movie>
) {

    fun getPopularList(): Flow<PagingData<Movie>>{
        return movieRepository.getMovies()
    }
}