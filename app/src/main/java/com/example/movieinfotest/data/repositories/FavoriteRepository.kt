package com.example.movieinfotest.data.repositories

import androidx.paging.*
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.utils.DataSourceMode
import com.example.movieinfotest.utils.converters.toActorData
import com.example.movieinfotest.utils.converters.toMovieDetails
import com.example.movieinfotest.utils.toActorDomain
import com.example.movieinfotest.utils.toMovieDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    val api: ApiHelper,
    val db: DbHelper
) : IFavoriteRepository<Movie> {

    override suspend fun getFavorite(movie_id: Int): Movie? {
        val actors = db.getActorsById(movie_id).toActorDomain()
        return db.getDetailsFromFavorite(movie_id)?.toMovieDomain(actors)
    }

    override fun getFavoriteList(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { db.getGetFavoriteList() }

        val p = Pager(
            config = PagingConfig(20, enablePlaceholders = true),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                Movie(
                    it.id,
                    it.title,
                    it.vote_average,
                    it.release_date,
                    it.poster_path,
                    it.overview,
                    null,
                    null
                )
            }
        }

        return p
    }

    override suspend fun saveInFavorite(movie: Movie, sourceMode: DataSourceMode) {
        if (sourceMode == DataSourceMode.ONLINE) {
            val movie = api.getDetailsInformation(movie.id.toString())
            if(movie!=null)
                db.saveInFavorite(movie, api.getActorsList(movie.id.toString()))
        }
        else
            db.saveInFavorite(movie.toMovieDetails(), movie.actors?.toActorData(movie.id))
    }

    override suspend fun deleteFromFavorite(movie_id: Int) {
        db.removeFromFavorite(movie_id)
    }
}