package com.example.movieinfotest.presentation.ui.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.RandomMovieUseCase
import com.example.movieinfotest.utils.network.NetworkConnection

class RandomViewModel(
    private val randomMovieUseCase: RandomMovieUseCase
) : ViewModel() {
    private val movieDetails: MutableLiveData<MovieDomain> by lazy {
        MutableLiveData<MovieDomain>()
    }
    private var genres: List<GenreDomain>? = null

    fun getRandom(): LiveData<MovieDomain> {
        return movieDetails
    }

    suspend fun generateRandom(genre: String, year: String) {
        val genreRes = if (genre != "0") genre else ""
        movieDetails.value = randomMovieUseCase.getRandomMovie(genre = genreRes, year = year)
    }

    suspend fun getGenres(): List<GenreDomain>? {
        if (genres == null)
            genres =
                randomMovieUseCase.getGenres(NetworkConnection.getNetworkStatus(MovieApp.getInstance()))
        return genres
    }
}
