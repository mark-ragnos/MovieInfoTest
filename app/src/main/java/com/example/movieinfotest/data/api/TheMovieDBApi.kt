package com.example.movieinfotest.data.api

import android.util.Log
import com.example.movieinfotest.data.entities.actors.FilmActors
import com.example.movieinfotest.data.entities.api.actor.ActorInfo
import com.example.movieinfotest.data.entities.details.MovieDetails
import com.example.movieinfotest.data.entities.genre.GenreCollection
import com.example.movieinfotest.data.entities.popular.PopularFilms
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApi {
    @GET("discover/movie?api_key=393a66787ff4b601eae377e5ec8b4d36&sort_by=popularity.desc")
    suspend fun discoverMoviesBy(
        @Query("year") year: String,
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<PopularFilms>

    @GET("movie/popular?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<PopularFilms>

    @GET("movie/{movie_id}?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String
    ): Response<MovieDetails>

    @GET("genre/movie/list?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getGenres(): Response<GenreCollection>

    @GET("movie/{movie_id}/credits?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getMovieCredits(@Path("movie_id") movieId: String): Response<FilmActors>

    @GET("person/{person_id}?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getActorInfo(@Path("person_id") actorId: Int): Response<ActorInfo>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): TheMovieDBApi {
            val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("OkHttp", message)
                }
            })
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(TheMovieDBApi::class.java)
        }
    }
}
