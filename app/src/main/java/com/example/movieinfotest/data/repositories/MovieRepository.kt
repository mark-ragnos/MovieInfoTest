package com.example.movieinfotest.data.repositories

import androidx.paging.*
import com.example.movieinfotest.data.api.MovieHelper
import com.example.movieinfotest.data.db.DatabaseHelper
import com.example.movieinfotest.data.db.MovieRemoteMediator
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.utils.toActorDomain
import com.example.movieinfotest.utils.toMovieDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    val api: MovieHelper,
    val db: DatabaseHelper
) : IMovieRepository<Movie> {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Movie>> {

        val pagingSourceFactory = { db.getDatabase().movieDao().loadMovies() }
        return Pager(
            config = PagingConfig(20, enablePlaceholders = true),
            remoteMediator = MovieRemoteMediator(api, db.getDatabase()),
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
    }

    override suspend fun getRandomMovie(genre: String, year: String): Movie {
        return api.getRandomMovie(year, genre)!!.toMovieDomain()
    }

    override suspend fun getMovieInfoLocal(movie_id: Int): Movie? {
        val favorite = db.getDetailsFromFavorite(movie_id)
        if (favorite != null) {
            val actors = db.getActorsById(movie_id).toActorDomain()
            return favorite.toMovieDomain(actors)
        }
        return db.getDetailsById(movie_id)?.toMovieDomain()
    }

    override suspend fun getMovieInfoRemote(movie_id: Int): Movie? {
        val favorite = db.getDetailsFromFavorite(movie_id)
        if (favorite != null) {
            val actors = db.getActorsById(movie_id).toActorDomain()
            return favorite.toMovieDomain(actors)
        }
        return api.getDetailsInformation(movie_id.toString())
            ?.toMovieDomain(api.getActorsList(movie_id.toString())?.toActorDomain())
    }

}