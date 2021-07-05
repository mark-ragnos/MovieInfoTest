package com.example.movieinfotest.presentation.di

import com.example.movieinfotest.domain.usecases.ActorUseCase
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.domain.usecases.GenreUseCase
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.presentation.di.modules.ActorModule
import com.example.movieinfotest.presentation.di.modules.FavoriteModule
import com.example.movieinfotest.presentation.di.modules.GenreModule
import com.example.movieinfotest.presentation.di.modules.MovieModule
import dagger.Component

@Component(modules = [ActorModule::class, MovieModule::class, GenreModule::class, FavoriteModule::class])
interface AppComponent {

    fun getFavoriteUseCase(): FavoriteMovieUseCase

    fun getMovieUseCase(): MovieUseCase

    fun getGenreUseCase(): GenreUseCase

    fun getActorUseCase(): ActorUseCase
}
