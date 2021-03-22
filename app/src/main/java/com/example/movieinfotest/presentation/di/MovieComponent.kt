package com.example.movieinfotest.presentation.di

import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.domain.usecases.RandomMovieUseCase
import com.example.movieinfotest.presentation.di.usecases.FavoriteModule
import com.example.movieinfotest.presentation.di.usecases.MovieInfoModule
import com.example.movieinfotest.presentation.di.usecases.PopularModule
import com.example.movieinfotest.presentation.di.usecases.RandomModule
import dagger.Component
import javax.inject.Singleton



@Component(modules = [FavoriteModule::class, MovieInfoModule::class, PopularModule::class, RandomModule::class])
interface MovieComponent {

    @Singleton
    fun getFavoriteMovieUseCase(): FavoriteMovieUseCase

    @Singleton
    fun getMovieInfoUseCase(): MovieInfoUseCase

    @Singleton
    fun getPopularMovieUseCase(): PopularMovieUseCase

    @Singleton
    fun getRandomMovieUseCase(): RandomMovieUseCase
}