package com.example.movieinfotest.domain.usecases

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.Flow

class FavoriteMovieUseCase(
    private val favoriteRepository: IFavoriteRepository<MovieDomain>
) {

    suspend fun isFavorite(movie_id: Int):Boolean{
        return favoriteRepository.getFavorite(movie_id) != null
    }

    suspend fun saveInFavorite(movie: MovieDomain, sourceMode: NetworkConnection.STATUS){
        favoriteRepository.saveInFavorite(movie, sourceMode)
    }

    fun getFavoriteList(): Flow<PagingData<MovieDomain>> {
        return favoriteRepository.getFavoriteList()
    }

    suspend fun deleteFromFavorite(movie_id: Int){
        favoriteRepository.deleteFromFavorite(movie_id)
    }
}
