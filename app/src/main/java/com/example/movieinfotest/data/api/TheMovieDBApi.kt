package com.example.movieinfotest.data.api

import android.util.Log
import com.example.movieinfotest.data.entities.actors.FilmActors
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
import java.util.concurrent.TimeUnit

interface TheMovieDBApi {

    //Генерация случайного фильма с заданными параметрами ГОД? ЖАНР
    //Отображать: Постер, название и рейтинг фильма
    @GET("discover/movie?api_key=393a66787ff4b601eae377e5ec8b4d36&sort_by=popularity.desc")
    suspend fun getRandomFilm(
        @Query("year") year: String,
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<PopularFilms>


    //Самые популярные фильмы в порядку убывания популярности
    //Отображать: Постер, название, рейтинг и год
    @GET("movie/popular?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getPopular(@Query("page") page: Int): Response<PopularFilms>


    //Полная инфа о фильме по ИД
    //Отображать: Постер, название, дата, рейтинг, жанр, описание, список актеров
    @GET("movie/{movie_id}?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getDetails(
        @Path("movie_id") movieId: String
    ): Response<MovieDetails>


    //Получение списка жанров
    @GET("genre/movie/list?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getGenreList(): Response<GenreCollection>


    //Получения списка актеров у фильма
    @GET("movie/{movie_id}/credits?api_key=393a66787ff4b601eae377e5ec8b4d36")
    suspend fun getCredits(@Path("movie_id") movieId: String): Response<FilmActors>


    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): TheMovieDBApi {
            val loger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("OkHttp", message)
                }
            })
            loger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .connectTimeout(2000, TimeUnit.SECONDS)
                .writeTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)
                .addInterceptor(loger)
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