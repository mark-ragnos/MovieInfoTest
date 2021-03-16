package com.example.movieinfotest.ui.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.models.popular.Movie
import kotlinx.coroutines.flow.Flow

class PopularViewModel(private val repository: Repository) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> =
        repository.getPopularNew().cachedIn(viewModelScope)

    fun getFavorite(): Flow<PagingData<Movie>> {
        return movies
    }
}