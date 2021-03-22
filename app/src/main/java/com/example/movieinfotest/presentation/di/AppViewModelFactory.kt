package com.example.movieinfotest.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.api.TheMovieDBApi
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.FavoriteRepository
import com.example.movieinfotest.data.repositories.GenreRepository
import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.domain.usecases.RandomMovieUseCase
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel

class AppViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java))
            return PopularViewModel(
                DaggerMovieComponent.builder().build().getPopularMovieUseCase(),
                DaggerMovieComponent.builder().build().getFavoriteMovieUseCase()
            ) as T
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java))
            return DetailsViewModel(
                DaggerMovieComponent.builder().build().getMovieInfoUseCase(),
                DaggerMovieComponent.builder().build().getFavoriteMovieUseCase()
            ) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(
                DaggerMovieComponent.builder().build().getFavoriteMovieUseCase()
            ) as T
        if (modelClass.isAssignableFrom(RandomViewModel::class.java))
            return RandomViewModel(
                DaggerMovieComponent.builder().build().getRandomMovieUseCase()
            ) as T

        throw IllegalArgumentException("Incorrect ViewModel class")
    }
}