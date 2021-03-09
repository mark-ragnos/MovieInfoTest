package com.example.movieinfotest.ui.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.Repository
import com.example.movieinfotest.network.responses.popular.Results

class RandomViewModel(private val repository: Repository) : ViewModel() {
    private val movieDetails: MutableLiveData<Results> by lazy {
        MutableLiveData<Results>()
    }

    fun getRandom(): LiveData<Results> {
        return movieDetails
    }

    suspend fun generateRandom(genre: String, year: String) {
        movieDetails.value = repository.getRandom(genre = genre, year = year)
    }
}