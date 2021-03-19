package com.example.movieinfotest.presentation.ui.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.domain.entities.actor.Actor
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.utils.DataSourceMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieInfoUseCase: MovieInfoUseCase,
    private val favoriteUseCase: FavoriteMovieUseCase
) : ViewModel() {

    private val movieDetails: MutableLiveData<Movie?> by lazy {
        MutableLiveData<Movie?>()
    }
    private val actorsList: MutableLiveData<List<Actor>> by lazy {
        MutableLiveData<List<Actor>>()
    }

    fun getDetails(): LiveData<Movie?> {
        return movieDetails
    }

    fun getActors(): LiveData<List<Actor>> {
        return actorsList
    }

    suspend fun sendID(id: Int) {
        movieDetails.value = movieInfoUseCase.getMovieInfo(id, DataSourceMode.ONLINE)
    }

    fun saveInFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDetails.value?.let { favoriteUseCase.saveInFavorite(it) }
        }
    }

    fun deleteFromFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDetails.value?.id?.let { favoriteUseCase.deleteFromFavorite(it) }
        }
    }

    suspend fun isFavorite(): Boolean {
        return movieInfoUseCase.isFavorite(movieDetails.value!!.id)
    }

}