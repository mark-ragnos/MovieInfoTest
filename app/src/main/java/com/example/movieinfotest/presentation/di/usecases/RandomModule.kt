package com.example.movieinfotest.presentation.di.usecases


import com.example.movieinfotest.data.repositories.GenreRepository
import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.usecases.RandomMovieUseCase
import com.example.movieinfotest.presentation.di.repository.GenreRepModule
import com.example.movieinfotest.presentation.di.repository.MovieRepModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module(includes = [GenreRepModule::class, MovieRepModule::class])
class RandomModule {


    @Provides
    fun getRandomMovieUseCase(
        genreRepository: GenreRepository,
        movieRepository: MovieRepository
    ): RandomMovieUseCase {
        return RandomMovieUseCase(genreRepository, movieRepository)
    }
}