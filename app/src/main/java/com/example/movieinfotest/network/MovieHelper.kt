package com.example.movieinfotest.network

import Genre
import MovieDetails
import Results
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

    suspend fun test(): String {
        var c = apiThe.getRandomFilm("2000", "12", 2)
        var res = c.body()
        if (res != null) {
            return res.results.size.toString()
        }
        return "ERROR"
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
        val response = apiThe.getRandomFilm(year, genre, Random.nextInt(1..1000))
        return response.body()?.results?.get(Random.nextInt(1..20))
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