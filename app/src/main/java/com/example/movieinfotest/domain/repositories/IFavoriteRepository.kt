package com.example.movieinfotest.domain.repositories

import androidx.paging.PagingData
import com.example.movieinfotest.utils.DataSourceMode
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository<T: Any> {

    suspend fun getFavorite(movie_id:Int):T?

    fun getFavoriteList(): Flow<PagingData<T>>

    suspend fun saveInFavorite(movie: T, sourceMode: DataSourceMode)

    suspend fun deleteFromFavorite(movie_id: Int)
}