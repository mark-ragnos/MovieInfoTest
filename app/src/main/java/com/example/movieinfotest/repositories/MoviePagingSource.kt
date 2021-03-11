package com.example.movieinfotest.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.models.popular.Movie
import java.lang.Exception

class MoviePagingSource(var repository: Repository) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val currencyPage = params.key ?: 1
            val response = repository.getPopular(currencyPage)
            val respdata = mutableListOf<Movie>()
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
}