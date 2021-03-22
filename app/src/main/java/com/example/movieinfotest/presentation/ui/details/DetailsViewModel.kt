package com.example.movieinfotest.presentation.ui.details


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.domain.entities.actor.Actor
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.utils.DataSourceMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieInfoUseCase: MovieInfoUseCase,
    private val favoriteUseCase: FavoriteMovieUseCase
) : ViewModel() {
    private var isFavorite = false

    private val movieDetails: MutableLiveData<Movie?> by lazy {
        MutableLiveData<Movie?>()
    }

    fun getDetails(): LiveData<Movie?> {
        return movieDetails
    }

    suspend fun sendID(id: Int, dataSourceMode: DataSourceMode) {
        movieDetails.value = movieInfoUseCase.getMovieInfo(id, dataSourceMode)
    }

    fun saveInFavorite(sourceMode: DataSourceMode) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDetails.value?.let { favoriteUseCase.saveInFavorite(it, sourceMode) }
        }
    }

    fun deleteFromFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDetails.value?.id?.let { favoriteUseCase.deleteFromFavorite(it) }
        }
    }

    suspend fun isFavorite(): Boolean {
        CoroutineScope(Dispatchers.IO).async {
            isFavorite = favoriteUseCase.isFavorite(movieDetails.value!!.id)
        }.await()
        return isFavorite
    }

}