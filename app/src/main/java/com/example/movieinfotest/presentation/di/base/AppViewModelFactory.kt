package com.example.movieinfotest.presentation.di.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.api.TheMovieDBApi
import com.example.movieinfotest.data.repositories.ActorRepository
import com.example.movieinfotest.domain.usecases.ActorUseCase
import com.example.movieinfotest.getComponent
import com.example.movieinfotest.presentation.di.AppComponent
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.details.actors.ActorViewModel
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel

class AppViewModelFactory(
    private val appComponent: AppComponent
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java))
            return PopularViewModel(
                appComponent.getMovieUseCase(),
                appComponent.getFavoriteUseCase()
            ) as T
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java))
            return DetailsViewModel(
                appComponent.getMovieUseCase(),
                appComponent.getFavoriteUseCase()
            ) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(appComponent.getFavoriteUseCase()) as T
        if (modelClass.isAssignableFrom(RandomViewModel::class.java))
            return RandomViewModel(
                appComponent.getMovieUseCase(),
                appComponent.getGenreUseCase()
            ) as T
        if (modelClass.isAssignableFrom(ActorViewModel::class.java))
            return ActorViewModel(ActorUseCase(ActorRepository(ApiHelper(TheMovieDBApi.create())))) as T

        throw IllegalArgumentException("Incorrect ViewModel class")
    }

    companion object {
        fun getFactory(context: Context): AppViewModelFactory {
            return AppViewModelFactory(
                context.getComponent()
            )
        }
    }
}
