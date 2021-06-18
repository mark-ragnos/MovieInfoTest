package com.example.movieinfotest.presentation.di

import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.GenreUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.presentation.di.usecases.FavoriteModule
import com.example.movieinfotest.presentation.di.usecases.GenreModule
import com.example.movieinfotest.presentation.di.usecases.MovieModule
import dagger.Component

@Component(modules = [FavoriteModule::class, MovieModule::class, GenreModule::class])
interface MovieComponent {

    fun getFavoriteUseCase(): FavoriteMovieUseCase

    fun getMovieUseCase(): MovieUseCase

    fun getGenreUseCase(): GenreUseCase
}
