package com.example.movieinfotest.presentation.di.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfotest.presentation.di.DaggerMovieComponent
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel

class AppViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java))
            return DaggerMovieComponent.builder().build().getPopularViewModel() as T
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java))
            return DaggerMovieComponent.builder().build().getDetailsViewModel() as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return DaggerMovieComponent.builder().build().getFavoriteViewModel() as T
        if (modelClass.isAssignableFrom(RandomViewModel::class.java))
            return DaggerMovieComponent.builder().build().getRandomViewModel() as T

        throw IllegalArgumentException("Incorrect ViewModel class")
    }
}