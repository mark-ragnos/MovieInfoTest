package com.example.movieinfotest.presentation.ui.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.utils.network.NetworkStatus
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

    suspend fun sendID(id: Int, networkStatus: NetworkStatus) {
        movieDetails.value = movieInfoUseCase.getMovieInfo(id, networkStatus)
    }

    fun saveInFavorite(sourceMode: NetworkStatus) {
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