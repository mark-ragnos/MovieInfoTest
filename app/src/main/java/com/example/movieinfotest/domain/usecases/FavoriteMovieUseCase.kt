package com.example.movieinfotest.domain.usecases

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteMovieUseCase(
    private val favoriteRepository: IFavoriteRepository<Movie>
) {

    suspend fun isFavorite(movie_id: Int):Boolean{
        return favoriteRepository.getFavorite(movie_id) != null
    }

    suspend fun saveInFavorite(movie: Movie){
        favoriteRepository.saveInFavorite(movie)
    }

    fun getFavoriteList(): Flow<PagingData<Movie>> {
        return favoriteRepository.getFavoriteList()
    }

    suspend fun deleteFromFavorite(movie_id: Int){
        favoriteRepository.deleteFromFavorite(movie_id)
    }
}