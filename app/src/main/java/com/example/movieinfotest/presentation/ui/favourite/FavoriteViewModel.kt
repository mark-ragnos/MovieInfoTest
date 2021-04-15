package com.example.movieinfotest.presentation.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> =
        favoriteMovieUseCase.getFavoriteList()

    fun getPopular(): Flow<PagingData<Movie>> {
        return movies
    }

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteMovieUseCase.deleteFromFavorite(id)
        }
    }
}