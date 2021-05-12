package com.example.movieinfotest.presentation.ui.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.utils.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val movieInfoUseCase: MovieInfoUseCase,
    private val favoriteUseCase: FavoriteMovieUseCase
) : ViewModel() {
    private var isFavorite = false
    private val movieDetails = MutableStateFlow<Movie?>(null)

    fun getDetails(): StateFlow<Movie?> {
        return movieDetails
    }

    fun sendID(id: Int, networkStatus: NetworkStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDetails.value = movieInfoUseCase.getMovieInfo(id, networkStatus)
        }
    }

    fun saveInFavorite(sourceMode: NetworkStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDetails.value?.let { favoriteUseCase.saveInFavorite(it, sourceMode) }
        }
    }

    fun deleteFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDetails.value?.id?.let { favoriteUseCase.deleteFromFavorite(it) }
        }
    }

    suspend fun isFavorite(): Boolean =
        withContext(viewModelScope.coroutineContext) {
            isFavorite = favoriteUseCase.isFavorite(movieDetails.value!!.id)
            isFavorite
        }

}