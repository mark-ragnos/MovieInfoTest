package com.example.movieinfotest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.ui.details.DetailsViewModel
import com.example.movieinfotest.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.ui.popular.PopularViewModel
import com.example.movieinfotest.ui.random.RandomViewModel

class AppViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java))
            return PopularViewModel(Repository.create()) as T
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java))
            return DetailsViewModel(Repository.create()) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(Repository.create()) as T
        if (modelClass.isAssignableFrom(RandomViewModel::class.java))
            return RandomViewModel(Repository.create()) as T

        throw IllegalArgumentException("Incorrect ViewModel class")
    }
}