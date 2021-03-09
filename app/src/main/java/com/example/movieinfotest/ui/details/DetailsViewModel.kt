package com.example.movieinfotest.ui.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.Repository
import com.example.movieinfotest.network.responses.actors.Actor
import com.example.movieinfotest.network.responses.details.MovieDetails

class DetailsViewModel(private val repository: Repository):ViewModel() {

    private val movieDetails:MutableLiveData<MovieDetails> by lazy {
        MutableLiveData<MovieDetails>()
    }
    private val actorsList:MutableLiveData<List<Actor>> by lazy {
        MutableLiveData<List<Actor>>()
    }

    fun getDetails():LiveData<MovieDetails>{
        return movieDetails
    }
    fun getActors():LiveData<List<Actor>>{
        return actorsList
    }

    suspend fun sendID(id:Int){
        movieDetails.value = repository.getDetails(id.toString())
        actorsList.value = repository.getActors(id.toString())
    }

}