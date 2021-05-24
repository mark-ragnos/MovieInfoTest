package com.example.movieinfotest.presentation.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PopularViewModel(
    private val movieUseCase: MovieUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {
    val movies: Flow<PagingData<MovieDomain>> =
        movieUseCase.getPopularMovies().cachedIn(viewModelScope)

    fun saveInFavorite(movie: MovieDomain, sourceMode: NetworkConnection.STATUS) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieUseCase.saveInFavorite(movie, sourceMode)
        }
    }

    fun removeFromFavorite(movie: MovieDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieUseCase.deleteFromFavorite(movie.id)
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        return favoriteMovieUseCase.isFavorite(id)
    }
}
