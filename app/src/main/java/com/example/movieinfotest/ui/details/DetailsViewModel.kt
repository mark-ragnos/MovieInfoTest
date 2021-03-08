package com.example.movieinfotest.ui.details


import MovieDetails
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.Repository

class DetailsViewModel(private val repository: Repository):ViewModel() {

    private val movieDetails:MutableLiveData<MovieDetails> by lazy {
        MutableLiveData<MovieDetails>()
    }

    fun getDetails():LiveData<MovieDetails>{
        return movieDetails
    }

    suspend fun sendID(id:Int){
        movieDetails.value = repository.getDetails(id.toString())
    }

}