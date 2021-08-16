package com.example.movieinfotest.domain.repositories

import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {

    suspend fun getFavorite(movieId: Int): MovieDomain?

    fun getFavoriteList(): Flow<PagingData<MovieDomain>>

    suspend fun saveInFavorite(movie: MovieDomain, sourceMode: NetworkConnection.STATUS)

    suspend fun deleteFromFavorite(movieId: Int)

    fun getFavoriteIds(): Flow<List<Int>>
}
