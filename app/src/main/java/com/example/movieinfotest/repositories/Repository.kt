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
        if (databaseHelper.getAllGenres().isEmpty())
            addAllGenres()
        return databaseHelper.getAllGenres()
    }

    private suspend fun addAllGenres() {
        databaseHelper.addAllGenres(apiHelper.getGenresList()!!)
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