package com.example.movieinfotest.ui.details


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.details.MovieDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val movieDetails: MutableLiveData<MovieDetails> by lazy {
        MutableLiveData<MovieDetails>()
    }
    private val actorsList: MutableLiveData<List<Actor>> by lazy {
        MutableLiveData<List<Actor>>()
    }

    fun getDetails(): LiveData<MovieDetails> {
        return movieDetails
    }

    fun getActors(): LiveData<List<Actor>> {
        return actorsList
    }

    suspend fun sendID(id: Int) {
        movieDetails.value = repository.getDetails(id.toString())
        actorsList.value = repository.getActors(id.toString())
    }

    fun saveInFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveInFavorite(movieDetails.value, actorsList.value)
        }
    }

}