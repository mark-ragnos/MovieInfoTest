package com.example.movieinfotest.ui.popular


import Results
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.*
import com.example.movieinfotest.Repository
import com.example.movieinfotest.listadapter.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import java.lang.RuntimeException

class PopularViewModel(private val repository: Repository) : ViewModel() {
    private val movies: Flow<PagingData<Results>> = Pager(PagingConfig(pageSize = 1)) {
        MoviePagingSource(Repository.create())
    }.flow

    fun getFavorite(): Flow<PagingData<Results>> {
        return movies
    }

    init {
        Log.d("TAG", "Create")
    }
}