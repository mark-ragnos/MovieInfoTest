package com.example.movieinfotest.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.properties.Delegates

class DetailsViewModel(
    private val movieUseCase: MovieUseCase,
    private val favoriteUseCase: FavoriteMovieUseCase
) : ViewModel() {
    private var movieId by Delegates.notNull<Int>()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _movieDetails = MutableStateFlow<MovieDomain?>(null)
    val movieDetails = _movieDetails.asStateFlow()

    fun sendID(id: Int, networkStatus: NetworkConnection.STATUS) {
        movieId = id

        if (movieDetails.value?.id != id) {
            loadMovie(networkStatus)
        }
    }

    fun loadMovie(networkStatus: NetworkConnection.STATUS) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.value = movieUseCase.getMovieInfo(movieId, networkStatus)
            isFavorite()
        }
    }

    fun saveInFavorite(sourceMode: NetworkConnection.STATUS) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDetails.value?.let {
                favoriteUseCase.saveInFavorite(it, sourceMode)
            }
            isFavorite()
        }
    }

    fun deleteFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDetails.value?.id?.let {
                favoriteUseCase.deleteFromFavorite(it)
            }
            isFavorite()
        }
    }

    private suspend fun isFavorite() {
        _isFavorite.emit(favoriteUseCase.isFavorite(movieId))
    }
}
