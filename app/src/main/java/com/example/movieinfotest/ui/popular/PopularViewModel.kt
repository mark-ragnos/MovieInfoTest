package com.example.movieinfotest.ui.popular


import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.repositories.MoviePagingSource
import com.example.movieinfotest.models.popular.Movie
import kotlinx.coroutines.flow.Flow

class PopularViewModel(private val repository: Repository) : ViewModel() {
    private val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 1)) {
        MoviePagingSource(Repository.create())
    }.flow

    fun getFavorite(): Flow<PagingData<Movie>> {
        //return movies
        return  repository.getPopularNew()
    }
}