package com.example.movieinfotest.repositories

import androidx.paging.*
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.database.DatabaseHelper
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.models.popular.PopularFilms
import com.example.movieinfotest.network.TheMovieDBApi
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: MovieHelper,
    private val databaseHelper: DatabaseHelper
) {

    suspend fun getPopular(page: Int): List<Movie>? {
        return apiHelper.getPopularList(page)
    }


    @OptIn(ExperimentalPagingApi::class)
    fun getPopularNew(): Flow<PagingData<Movie>> {
        val database = MovieApp.getInstance().getDatabase()

        val pagingSourceFactory = { database.movieDao().loadMovies() }
        return Pager(
            config = PagingConfig(20, enablePlaceholders = true),
            remoteMediator = MovieRemoteMediator(apiHelper, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getRandom(year: String, genre: String): Movie? {
        return apiHelper.getRandomMovie(year, genre)
    }

    //Совместить с БД

    suspend fun getDetails(id: String): MovieDetails? {
        return apiHelper.getDetailsInformation(id)
    }

    suspend fun getActors(filmId: String): List<Actor>? {
        return apiHelper.getActorsList(filmId)
    }

    suspend fun getAllGenres(): List<Genre> {
        databaseHelper.addAllGenres(apiHelper.getGenresList()!!)
        return databaseHelper.getAllGenres()
    }

    suspend fun getFavorite(): List<Movie> {
        return ArrayList<Movie>()
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