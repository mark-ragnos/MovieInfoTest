package com.example.movieinfotest.network

import android.util.Log
import androidx.paging.PagingData
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.models.popular.Movie
import java.util.concurrent.Flow
import kotlin.random.Random
import kotlin.random.nextInt

class MovieHelper {
    val api: TheMovieDBApi = TheMovieDBApi.create()

    suspend fun getPopularList(page: Int): List<Movie>? {
        val response = api.getPopular(page)
        return response.body()?.results
    }

    suspend fun getDetailsInformation(id: String): MovieDetails? {
        val response = api.getDetails(id)
        return response.body()
    }

    suspend fun getRandomMovie(year: String, genre: String): Movie? {
        val pages = api.getRandomFilm(year, genre, 1).body()!!.total_pages
        val response = api.getRandomFilm(year, genre, Random.nextInt(1..pages)).body()
        if (response == null) Log.d("TEST", "TIME")
        return response!!.results.get(Random.nextInt(response!!.results.indices))
    }

    suspend fun getGenresList(): List<Genre>? {
        val response = api.getGenreList()
        return response.body()?.genres
    }

    suspend fun getActorsList(id: String): List<Actor>? {
        val response = api.getCredits(id)
        return response.body()?.cast
    }
}