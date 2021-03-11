package com.example.movieinfotest.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.movieinfotest.database.DatabaseHelper
import com.example.movieinfotest.database.MovieDatabase
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.network.TheMovieDBApi
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieService: TheMovieDBApi,
    private val database: MovieDatabase
):RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try{
            val response = movieService.getPopular(state.config.pageSize)

            val movieBody = response.body()
            val movies = movieBody?.results
            if(movies!=null)
                database.movieDao().saveMovieList(movies)

            MediatorResult.Success(endOfPaginationReached = movieBody!!.page >= movieBody.total_pages)
        }catch (e :IOException){
            MediatorResult.Error(e)
        }
        catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }
}