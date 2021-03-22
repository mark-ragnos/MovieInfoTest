package com.example.movieinfotest.presentation.di

import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.domain.usecases.RandomMovieUseCase
import com.example.movieinfotest.presentation.di.usecases.FavoriteModule
import com.example.movieinfotest.presentation.di.usecases.MovieInfoModule
import com.example.movieinfotest.presentation.di.usecases.PopularModule
import com.example.movieinfotest.presentation.di.usecases.RandomModule
import com.example.movieinfotest.presentation.di.viewmodels.FavoriteVmModule
import com.example.movieinfotest.presentation.di.viewmodels.MovieInfoVmModule
import com.example.movieinfotest.presentation.di.viewmodels.PopularVmModule
import com.example.movieinfotest.presentation.di.viewmodels.RandomVmModule
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = [FavoriteVmModule::class, RandomVmModule::class, PopularVmModule::class, MovieInfoVmModule::class])
interface MovieComponent {

    @Singleton
    fun getFavoriteMovieUseCase(): FavoriteViewModel

    @Singleton
    fun getMovieInfoUseCase(): DetailsViewModel

    @Singleton
    fun getPopularMovieUseCase(): PopularViewModel

    @Singleton
    fun getRandomMovieUseCase(): RandomViewModel
}