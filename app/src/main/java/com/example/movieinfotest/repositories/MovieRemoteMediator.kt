package com.example.movieinfotest.repositories

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieinfotest.database.MovieDatabase
import com.example.movieinfotest.database.movie.RemoteKeys
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.network.TheMovieDBApi
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import java.lang.Exception


@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: MovieHelper,
    private val db: MovieDatabase
) : RemoteMediator<Int, Movie>() {
    private val startPage: Int = 1


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: startPage
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
            }
            val movies = api.getPopularList(page)!!

            val endOfPaginationReached = movies.size < state.config.pageSize

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteDao().clearRemoteKeys()
                    db.movieDao().clear()
                }

                val prevKey = if (page == startPage) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RemoteKeys(it.id, prevKey, nextKey)
                }

                db.remoteDao().insertAll(keys)
                db.movieDao().saveMovieList(movies)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.lastItemOrNull()?.let { it ->
            db.withTransaction {
                db.remoteDao().remoteKeysById(it.id)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.withTransaction { db.remoteDao().remoteKeysById(id) }
            }
        }
    }
}