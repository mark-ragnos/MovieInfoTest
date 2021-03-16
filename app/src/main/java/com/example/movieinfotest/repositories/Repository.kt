package com.example.movieinfotest.repositories

import android.util.Log
import androidx.paging.*
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.database.DatabaseHelper
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.models.popular.PopularFilms
import com.example.movieinfotest.network.TheMovieDBApi
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: MovieHelper,
    private val databaseHelper: DatabaseHelper
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularNew(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { databaseHelper.getDatabase().movieDao().loadMovies() }
        return Pager(
            config = PagingConfig(20, enablePlaceholders = true),
            remoteMediator = MovieRemoteMediator(apiHelper, databaseHelper.getDatabase()),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getRandom(year: String, genre: String): Movie? {

        val isOnline = MainActivity.isOnline(MovieApp.getInstance())
        if (!isOnline)
            TODO()

        return apiHelper.getRandomMovie(year, genre)
    }

    suspend fun getDetails(id: String): MovieDetails? {

        val favoriteDetails = databaseHelper.getDetailsFromFavorite(id.toInt())
        if (favoriteDetails != null)
            return favoriteDetails

        val isOnline = MainActivity.isOnline(MovieApp.getInstance())
        if (!isOnline)
            return databaseHelper.getDetailsById(id.toInt())

        return apiHelper.getDetailsInformation(id)
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

    suspend fun getFavorite(): List<MovieDetailsDB>? {
        return databaseHelper.getGetFavoriteList()
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