package com.example.movieinfotest.data.repositories

import androidx.paging.PagingData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.utils.converters.toActorData
import com.example.movieinfotest.utils.converters.toMovieDetails
import com.example.movieinfotest.utils.converters.toMovieDomain
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.network.isOnline
import com.example.movieinfotest.utils.toActorDomain
import com.example.movieinfotest.utils.toMovieDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    val api: ApiHelper,
    val db: DbHelper
) : IFavoriteRepository<MovieDomain> {

    override suspend fun getFavorite(movieId: Int): MovieDomain? {
        val actors = db.getActorsById(movieId).toActorDomain()
        return db.getDetailsFromFavorite(movieId)?.toMovieDomain(actors)
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
            val movie = api.getDetailsInformation(movie.id.toString())
            movie?.let {
                db.saveInFavorite(movie, api.getActorsList(movie.id.toString()))
            }
        } else {
            db.saveInFavorite(movie.toMovieDetails(), movie.actors?.toActorData(movie.id))
        }
    }

    override suspend fun deleteFromFavorite(movieId: Int) {
        db.removeFromFavorite(movieId)
    }
}
