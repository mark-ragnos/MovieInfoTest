package com.example.movieinfotest.network

import Genre
import MovieDetails
import Results
import android.util.Log
import com.example.movieinfotest.network.responses.actors.Actor
import kotlin.random.Random
import kotlin.random.nextInt

class MovieHelper {

    val URL = "https://api.themoviedb.org/3/"

    //var movieAli: MovieDatabaseApi
    val apiThe: TheMovieDBApi

    constructor() {
        apiThe = TheMovieDBApi.create()
    }


    suspend fun getPopularList(page: Int): List<Results>? {
        val response = apiThe.getPopular(page)
        return response.body()?.results
    }

    suspend fun getDetailsInformation(id: String): MovieDetails? {
        val response = apiThe.getDetails(id)
        return response.body()
    }

    suspend fun getRandomMovie(year: String, genre: String): Results? {
        val pages = apiThe.getRandomFilm(year, genre, 1).body()!!.total_pages
        val response = apiThe.getRandomFilm(year, genre, Random.nextInt(1..pages)).body()
        if (response == null) Log.d("TEST", "TIME")
        return response!!.results.get(Random.nextInt(response!!.results.indices))
    }

    suspend fun getGenresList(): List<Genre>? {
        val response = apiThe.getGenreList()
        return response.body()
    }

    suspend fun getActorsList(id: String): List<Actor>? {
        val response = apiThe.getCredits(id)
        return response.body()?.cast
    }
}