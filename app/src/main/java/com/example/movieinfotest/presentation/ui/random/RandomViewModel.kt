package com.example.movieinfotest.presentation.ui.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.data.entities.genre.Genre
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.data.entities.popular.Movie

class RandomViewModel(private val repository: Repository) : ViewModel() {
    private val movieDetails: MutableLiveData<Movie> by lazy {
        MutableLiveData<Movie>()
    }
    var genres:List<Genre>? = null

    fun getRandom(): LiveData<Movie> {
        return movieDetails
    }

    suspend fun generateRandom(genre: String, year: String) {
        val genreRes = if(genre != "0") genre else ""
        movieDetails.value = repository.getRandom(genre = genreRes, year = year)
    }

    suspend fun getGenres(): List<Genre>? {
        if(genres == null)
            genres = repository.getAllGenres()
        return genres
    }
}