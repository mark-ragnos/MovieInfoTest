package com.example.movieinfotest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieinfotest.listadapter.MoviePagingSource
import com.example.movieinfotest.network.MovieHelper

class MovieViewModel(
    private val movieHelper:MovieHelper
): ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 6)) {
        MoviePagingSource(movieHelper)
    }.flow.cachedIn(viewModelScope)
}