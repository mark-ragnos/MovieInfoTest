package com.example.movieinfotest.presentation.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
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
