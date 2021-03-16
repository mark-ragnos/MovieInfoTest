package com.example.movieinfotest.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.models.popular.Movie
import java.lang.Exception

class MovieDetailsDbPagingSource(var repository: Repository) : PagingSource<Int, MovieDetailsDB>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetailsDB> {
        try {
            val currencyPage = params.key ?: 1
            val response = repository.getFavorite() // add selector
            val respdata = mutableListOf<MovieDetailsDB>()
            if (response != null) {
                respdata.addAll(response)
            }
            val prevKey = if (currencyPage == 1) null else currencyPage - 1

            return LoadResult.Page(
                data = respdata,
                prevKey = prevKey,
                nextKey = currencyPage.plus(1)
            )
        } catch (
            e: Exception
        ) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDetailsDB>): Int? {
        return state.anchorPosition
    }
}