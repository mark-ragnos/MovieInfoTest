package com.example.movieinfotest.data.repositories

import androidx.paging.PagingData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.utils.converters.toMovieDomain
import com.example.movieinfotest.utils.converters.toMovieDetails
import com.example.movieinfotest.utils.converters.toCastData
import com.example.movieinfotest.utils.converters.toCrewData
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.network.isOnline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    val api: ApiHelper,
    val db: DbHelper
) : IFavoriteRepository {

    override suspend fun getFavorite(movieId: Int): MovieDomain? {
        return db.getDetailsFromFavorite(movieId)?.toMovieDomain()
    }

    override fun getFavoriteList(): Flow<PagingData<MovieDomain>> {
        val pagingSourceFactory = { db.getGetFavoriteList() }

        val p = Pager(
            config = PagingConfig(ApiHelper.PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovieDomain()
            }
        }

        return p
    }

    override suspend fun saveInFavorite(movie: MovieDomain, sourceMode: NetworkConnection.STATUS) {
        if (sourceMode.isOnline()) {
            saveInFavoriteOnline(movie)
        } else {
            saveInFavoriteOffline(movie)
        }
    }

    private suspend fun saveInFavoriteOnline(movie: MovieDomain) {
        if (movie.casts.isNullOrEmpty() && movie.crews.isNullOrEmpty()) {
            val movieDetails = api.getDetailsInformation(movie.id.toString())
            movieDetails?.let {
                val credits = api.getCredits(movie.id.toString())
                db.saveInFavorite(movieDetails, credits?.cast, credits?.crew)
            }
        } else {
            saveInFavoriteOffline(movie)
        }
    }

    private suspend fun saveInFavoriteOffline(movie: MovieDomain) {
        db.saveInFavorite(
            movie.toMovieDetails(),
            movie.casts?.toCastData(movie.id),
            movie.crews?.toCrewData(movie.id)
        )
    }

    override suspend fun deleteFromFavorite(movieId: Int) {
        db.removeFromFavorite(movieId)
    }

    override fun getFavoriteIds(): Flow<List<Int>> {
        return db.getAllFavoriteIds()
    }
}
