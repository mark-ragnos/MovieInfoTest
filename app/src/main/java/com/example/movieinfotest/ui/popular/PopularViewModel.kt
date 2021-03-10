package com.example.movieinfotest.ui.popular


import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.example.movieinfotest.Repository
import com.example.movieinfotest.listadapter.MoviePagingSource
import com.example.movieinfotest.network.responses.popular.Movie
import kotlinx.coroutines.flow.Flow

class PopularViewModel(private val repository: Repository) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 1)) {
        MoviePagingSource(Repository.create())
    }.flow

    fun getFavorite(): Flow<PagingData<Movie>> {
        return movies
    }
}