package com.example.movieinfotest.presentation.di.viewmodels

import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.presentation.di.usecases.FavoriteModule
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import dagger.Module
import dagger.Provides


@Module(includes = [FavoriteModule::class])
class FavoriteVmModule {

    @Provides
    fun getViewModel(favoriteMovieUseCase: FavoriteMovieUseCase): FavoriteViewModel {
        return FavoriteViewModel(favoriteMovieUseCase)
    }
}