package com.example.movieinfotest.domain.repositories

import androidx.paging.PagingData
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository<T : Any> {

    suspend fun getFavorite(movieId: Int): T?

    fun getFavoriteList(): Flow<PagingData<T>>

    suspend fun saveInFavorite(movie: T, sourceMode: NetworkConnection.STATUS)

    suspend fun deleteFromFavorite(movieId: Int)
}
