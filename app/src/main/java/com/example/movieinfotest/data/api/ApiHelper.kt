package com.example.movieinfotest.data.api

import com.example.movieinfotest.data.entities.actors.Actor
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.popular.Movie
import kotlin.random.Random
import kotlin.random.nextInt

class ApiHelper(
    private val api: TheMovieDBApi
) {

    suspend fun getPopularList(page: Int): List<Movie>? {
        val response = api.getPopular(page)
        return response.body()?.results
    }

    suspend fun getDetailsInformation(id: String): MovieDetails? {
        val response = api.getDetails(id)
        return response.body()
    }

    suspend fun getRandomMovie(year: String, genre: String): Movie? {
        val pages = api.getRandomFilm(year, genre, 1).body()!!.totalPages
        val response = api.getRandomFilm(year, genre, Random.nextInt(1..pages)).body()
        return response?.results?.get(Random.nextInt(response.results.indices))
    }

    suspend fun getGenresList(): List<Genre>? {
        val response = api.getGenreList()
        return response.body()?.genres
    }

    suspend fun getActorsList(id: String): List<Actor>? {
        val response = api.getCredits(id)
        return response.body()?.cast
    }

    companion object{
        const val PAGE_SIZE = 20
    }
}
