package com.example.movieinfotest.ui.details


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.utils.MovieFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val movieDetails: MutableLiveData<MovieFrom<MovieDetails?>> by lazy {
        MutableLiveData<MovieFrom<MovieDetails?>>()
    }
    private val actorsList: MutableLiveData<List<Actor>> by lazy {
        MutableLiveData<List<Actor>>()
    }

    fun getDetails(): LiveData<MovieFrom<MovieDetails?>> {
        return movieDetails
    }

    fun getActors(): LiveData<List<Actor>> {
        return actorsList
    }

    suspend fun sendID(id: Int) {
        if(movieDetails.value?.movie?.genres == null)
            movieDetails.value = repository.getDetails(id.toString())
        if(actorsList.value == null)
            actorsList.value = repository.getActors(id.toString())
    }

    fun saveInFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveInFavorite(movieDetails.value?.movie, actorsList.value)
        }
    }

    fun deleteFromFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFromFavorite(movieDetails.value!!.movie!!.id)
        }
    }

    fun changeIsFavorite(){
        movieDetails.value!!.isFromFavorite = !movieDetails.value!!.isFromFavorite
    }

    fun isFavorite():Boolean{
        return movieDetails.value!!.isFromFavorite
    }

}