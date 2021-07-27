package com.example.movieinfotest.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {
    val movies: Flow<PagingData<MovieDomain>> =
        favoriteMovieUseCase.getFavoriteList()

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieUseCase.deleteFromFavorite(id)
        }
    }
}
