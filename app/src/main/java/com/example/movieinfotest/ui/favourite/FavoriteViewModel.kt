package com.example.movieinfotest.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.repositories.Repository
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private var favoriteList: List<MovieDetailsDB>? = null

    private val mutableLiveData:MutableLiveData<List<MovieDetailsDB>> by lazy {
        MutableLiveData<List<MovieDetailsDB>>()
    }

    suspend fun getFavoirte(): List<MovieDetailsDB>? {
        if (favoriteList == null)
            favoriteList = repository.getFavorite()
        return favoriteList
    }

    suspend fun loadMovies(){
        mutableLiveData.value = repository.getFavorite()
    }

    fun getMovies():LiveData<List<MovieDetailsDB>>{
        return mutableLiveData
    }
}