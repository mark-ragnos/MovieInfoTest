package com.example.movieinfotest.data.api

import com.example.movieinfotest.BuildConfig
import com.example.movieinfotest.data.entities.actors.FilmActors
import com.example.movieinfotest.data.entities.api.actor.ActorInfo
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.genre.GenreCollection
import com.example.movieinfotest.data.entities.popular.PopularFilms
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovie {
    @GET("discover/movie?api_key=$API_KEY&sort_by=popularity.desc")
    suspend fun discoverMoviesBy(
        @Query("year") year: String,
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<PopularFilms>

    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<PopularFilms>

    @GET("movie/{movie_id}?api_key=$API_KEY")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String
    ): Response<MovieDetails>

    @GET("genre/movie/list?api_key=$API_KEY")
    suspend fun getGenres(): Response<GenreCollection>

    @GET("movie/{movie_id}/credits?api_key=$API_KEY")
    suspend fun getMovieCredits(@Path("movie_id") movieId: String): Response<FilmActors>

    @GET("person/{person_id}?api_key=$API_KEY")
    suspend fun getActorInfo(@Path("person_id") actorId: Int): Response<ActorInfo>

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}
