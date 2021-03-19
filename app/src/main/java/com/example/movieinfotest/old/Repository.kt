package com.example.movieinfotest.old

import androidx.paging.*
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.data.db.DatabaseHelper
import com.example.movieinfotest.data.api.MovieHelper
import com.example.movieinfotest.data.db.MovieRemoteMediator
import com.example.movieinfotest.data.entities.actors.Actor
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.details.MovieDetailsDB
import com.example.movieinfotest.data.entities.popular.Movie
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: MovieHelper,
    private val databaseHelper: DatabaseHelper
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopular(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { databaseHelper.getDatabase().movieDao().loadMovies() }
        return Pager(
            config = PagingConfig(20, enablePlaceholders = true),
            remoteMediator = MovieRemoteMediator(apiHelper, databaseHelper.getDatabase()),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getRandom(year: String, genre: String): Movie? {
        return apiHelper.getRandomMovie(year, genre)
    }

    suspend fun getDetails(id: String): MovieFrom<MovieDetails?> {
        val isOnline = MainActivity.isOnline(MovieApp.getInstance())

        val favoriteDetails = databaseHelper.getDetailsFromFavorite(id.toInt())
        if (favoriteDetails != null) {
            if (favoriteDetails.genres!!.isEmpty() && isOnline) {
                val movie = apiHelper.getDetailsInformation(id)
                saveInFavorite(movie, null)
                return MovieFrom(movie, true)
            }
            return MovieFrom(favoriteDetails, true)
        }
        if (!isOnline)
            return MovieFrom(databaseHelper.getDetailsById(id.toInt()), false)

        return MovieFrom(apiHelper.getDetailsInformation(id), false)
    }

    suspend fun getActors(filmId: String): List<Actor>? {

        val actors = databaseHelper.getActorsById(filmId.toInt())
        if (actors.isNotEmpty())
            return actors

        val isOnline = MainActivity.isOnline(MovieApp.getInstance())
        if (isOnline)
            return apiHelper.getActorsList(filmId)

        return null
    }

    suspend fun getAllGenres(): List<Genre>? {
        if (MainActivity.isOnline(MovieApp.getInstance()))
            databaseHelper.addAllGenres(apiHelper.getGenresList()!!)
        return databaseHelper.getAllGenres()
    }

    //FAVORITE
    fun getFavorite():Flow<PagingData<MovieDetailsDB>>{
        val pagingSourceFactory = { databaseHelper.getGetFavoriteList() }
        return Pager(
            config = PagingConfig(20, enablePlaceholders = true),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun saveInFavorite(movieDetails: MovieDetails?, actors: List<Actor>?) {
        if (movieDetails != null) {
            val isOnline = MainActivity.isOnline(MovieApp.getInstance())
            if (isOnline) {
                databaseHelper.saveInFavorite(
                    movieDetails,
                    actors ?: apiHelper.getActorsList(movieDetails.id.toString())
                )
            } else {
                databaseHelper.saveInFavorite(
                    movieDetails,
                    null
                )
            }
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        return databaseHelper.isFavorite(id)
    }

    suspend fun deleteFromFavorite(id: Int) {
        databaseHelper.removeFromFavorite(id)
    }


    companion object {
        private var mainRepository: Repository? = null

        fun create(): Repository {
            if (mainRepository == null) {
                mainRepository = Repository(MovieHelper(), DatabaseHelper())
            }
            return mainRepository!!
        }
    }
}