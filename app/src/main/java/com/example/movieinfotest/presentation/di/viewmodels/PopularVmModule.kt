package com.example.movieinfotest.presentation.di.viewmodels

import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.presentation.di.usecases.FavoriteModule
import com.example.movieinfotest.presentation.di.usecases.PopularModule
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import dagger.Module
import dagger.Provides


@Module(includes = [PopularModule::class, FavoriteModule::class])
class PopularVmModule {

    @Provides
    fun getViewModel(
        popularMovieUseCase: PopularMovieUseCase,
        favoriteMovieUseCase: FavoriteMovieUseCase
    ): PopularViewModel {
        return PopularViewModel(popularMovieUseCase, favoriteMovieUseCase)
    }
}