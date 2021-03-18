package com.example.movieinfotest.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.repositories.Repository
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private val movies: Flow<PagingData<MovieDetailsDB>> =
        repository.getFavorite()

    fun getPopular(): Flow<PagingData<MovieDetailsDB>> {
        return movies
    }
}