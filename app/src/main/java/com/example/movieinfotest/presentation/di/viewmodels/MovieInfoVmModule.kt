package com.example.movieinfotest.presentation.di.viewmodels

import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.presentation.di.usecases.FavoriteModule
import com.example.movieinfotest.presentation.di.usecases.MovieInfoModule
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import dagger.Module
import dagger.Provides


@Module(includes = [FavoriteModule::class, MovieInfoModule::class])
class MovieInfoVmModule {

    @Provides
    fun getViewModel(
        favoriteUseCase: FavoriteMovieUseCase,
        movieInfoUseCase: MovieInfoUseCase
    ): DetailsViewModel {
        return DetailsViewModel(movieInfoUseCase, favoriteUseCase)
    }
}