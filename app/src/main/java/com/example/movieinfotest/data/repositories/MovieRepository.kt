package com.example.movieinfotest.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.db.MovieRemoteMediator
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.network.isOnline
import com.example.movieinfotest.utils.converters.toCastDomain
import com.example.movieinfotest.utils.converters.toCrewDomain
import com.example.movieinfotest.utils.converters.toMovieDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    val api: ApiHelper,
    val db: DbHelper
) : IMovieRepository<MovieDomain> {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<MovieDomain>> {

        val pagingSourceFactory = { db.loadMovies() }
        return Pager(
            config = PagingConfig(ApiHelper.PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = MovieRemoteMediator(api, db.getDatabase()),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovieDomain()
            }
        }
    }

    override suspend fun getRandomMovie(genre: String, year: String): MovieDomain {
        return api.getRandomMovie(year, genre)!!.toMovieDomain()
    }

    override suspend fun getMovieInfo(
        movieId: Int,
        networkStatus: NetworkConnection.STATUS
    ): MovieDomain? {
        if (networkStatus.isOnline()) {
            return getMovieInfoRemote(movieId)
        }
        return db.getDetailsFromFavorite(movieId)?.toMovieDomain()
    }

    private suspend fun getMovieInfoRemote(movieId: Int): MovieDomain {
        return updateMovie(movieId)
    }

    private suspend fun updateMovie(movieId: Int): MovieDomain {
        val movieInfo = api.getDetailsInformation(movieId.toString())
        val credits = api.getCredits(movieId.toString())
        if (movieInfo != null) {
            db.saveInFavorite(movieInfo, credits?.cast, credits?.crew)
        }

        return movieInfo!!.toMovieDomain(
            credits?.cast?.toCastDomain(),
            credits?.crew?.toCrewDomain()
        )
    }
}
