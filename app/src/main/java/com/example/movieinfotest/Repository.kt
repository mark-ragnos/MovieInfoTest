package com.example.movieinfotest

import Genre
import MovieDetails
import Results
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.network.responses.actors.Actor

class Repository(private val apiHelper: MovieHelper) {

    suspend fun getPopular(page: Int): List<Results>? {
        return apiHelper.getPopularList(page)
    }

    suspend fun getRandom(year:String, genre:String): Results? {
        return apiHelper.getRandomMovie(year, genre)
    }

    //Совместить с БД

    suspend fun getDetails(id: String):MovieDetails?{
        return apiHelper.getDetailsInformation(id)
    }

    suspend fun getActors(filmId: String):List<Actor>?{
        return apiHelper.getActorsList(filmId)
    }

    suspend fun getAllGenres():List<Genre>{
        return apiHelper.getGenresList()!!
    }

    suspend fun getFavorite():List<Results>{
        return ArrayList<Results>()
    }



    companion object {
        private var mainRepository: Repository? = null

        fun create(): Repository {
            if (mainRepository == null) {
                mainRepository = Repository(MovieHelper())
            }
            return mainRepository!!
        }
    }
}