package com.example.movieinfotest.presentation.di.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.api.TheMovieDBApi
import com.example.movieinfotest.data.repositories.ActorRepository
import com.example.movieinfotest.domain.usecases.ActorUseCase
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.GenreUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.presentation.di.DaggerMovieComponent
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.details.actors.ActorViewModel
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel

class AppViewModelFactory(
    private val genreUseCase: GenreUseCase,
    private val movieUseCase: MovieUseCase,
    private val favoriteUseCase: FavoriteMovieUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java))
            return PopularViewModel(movieUseCase, favoriteUseCase) as T
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java))
            return DetailsViewModel(movieUseCase, favoriteUseCase) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(favoriteUseCase) as T
        if (modelClass.isAssignableFrom(RandomViewModel::class.java))
            return RandomViewModel(movieUseCase, genreUseCase) as T
        if (modelClass.isAssignableFrom(ActorViewModel::class.java))
            return ActorViewModel(ActorUseCase(ActorRepository(ApiHelper(TheMovieDBApi.create())))) as T

        throw IllegalArgumentException("Incorrect ViewModel class")
    }

    companion object {
        fun makeFactory(): AppViewModelFactory {
            val daggerComponent = DaggerMovieComponent.builder().build()

            return AppViewModelFactory(
                daggerComponent.getGenreUseCase(),
                daggerComponent.getMovieUseCase(),
                daggerComponent.getFavoriteUseCase()
            )
        }
    }
}
