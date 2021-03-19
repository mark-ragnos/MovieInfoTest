package com.example.movieinfotest.domain.usecases

import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.utils.DataSourceMode

class MovieInfoUseCase(
    private val favoriteRepository: IFavoriteRepository<Movie>,
    private val movieRepository: IMovieRepository<Movie>
){

    suspend fun isFavorite(movie_id: Int):Boolean{
        return favoriteRepository.getFavorite(movie_id) != null
    }

    suspend fun getMovieInfo(movie_id: Int, dataSourceMode: DataSourceMode): Movie? {
        if(dataSourceMode == DataSourceMode.OFFLINE)
            return movieRepository.getMovieInfoLocal(movie_id)
        return movieRepository.getMovieInfoRemote(movie_id)
    }
}