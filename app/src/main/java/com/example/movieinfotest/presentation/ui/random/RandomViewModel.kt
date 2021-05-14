package com.example.movieinfotest.presentation.ui.random

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.RandomMovieUseCase
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class RandomViewModel(
    private val randomMovieUseCase: RandomMovieUseCase
) : ViewModel() {
    private val movieDetails: MutableLiveData<Movie> by lazy {
        MutableLiveData<Movie>()
    }
    private var genres: List<Genre>? = null

    fun getRandom(): LiveData<Movie> {
        return movieDetails
    }

    suspend fun generateRandom(genre: String, year: String) {
        val genreRes = if (genre != "0") genre else ""
        movieDetails.value = randomMovieUseCase.getRandomMovie(genre = genreRes, year = year)
    }

    suspend fun getGenres(): List<Genre>? {
        if (genres == null)
            genres =
                randomMovieUseCase.getGenres(NetworkConnection.isOnline())
        return genres
    }
}