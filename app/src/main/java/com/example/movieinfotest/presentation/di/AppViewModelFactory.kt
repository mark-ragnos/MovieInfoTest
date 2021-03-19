package com.example.movieinfotest.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfotest.data.api.MovieHelper
import com.example.movieinfotest.data.db.DatabaseHelper
import com.example.movieinfotest.data.repositories.FavoriteRepository
import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel

class AppViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java))
            return PopularViewModel(Repository.create()) as T
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java))
            return DetailsViewModel(
                MovieInfoUseCase(
                    FavoriteRepository(MovieHelper(), DatabaseHelper()),
                    MovieRepository(MovieHelper(), DatabaseHelper())
                ),
                FavoriteMovieUseCase(
                    FavoriteRepository(MovieHelper(), DatabaseHelper())
                )
            ) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(
                FavoriteMovieUseCase(
                    FavoriteRepository(
                        MovieHelper(),
                        DatabaseHelper()
                    )
                )
            ) as T
        if (modelClass.isAssignableFrom(RandomViewModel::class.java))
            return RandomViewModel(Repository.create()) as T

        throw IllegalArgumentException("Incorrect ViewModel class")
    }
}