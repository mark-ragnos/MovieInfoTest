package com.example.movieinfotest.domain.usecases

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.Flow

class PopularMovieUseCase(
    private val movieRepository: IMovieRepository<Movie>,
    private val favoriteRepository: IFavoriteRepository<Movie>
) {

    suspend fun getPopularList(): Flow<PagingData<Movie>>{
        return movieRepository.getMovies()
    }

    suspend fun isFavorite(movie_id: Int): Boolean{
        return favoriteRepository.getFavorite(movie_id) != null
    }
}