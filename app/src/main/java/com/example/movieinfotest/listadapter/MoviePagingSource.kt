package com.example.movieinfotest.listadapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieinfotest.Repository
import com.example.movieinfotest.network.responses.popular.Results
import java.lang.Exception

class MoviePagingSource(var repository: Repository) : PagingSource<Int, Results>() {

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        try {
            val currencyPage = params.key ?: 1
            val response = repository.getPopular(currencyPage)
            val respdata = mutableListOf<Results>()
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