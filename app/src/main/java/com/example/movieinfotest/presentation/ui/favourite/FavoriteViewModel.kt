package com.example.movieinfotest.presentation.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> =
        favoriteMovieUseCase.getFavoriteList()

    fun getPopular(): Flow<PagingData<Movie>> {
        return movies
    }
}