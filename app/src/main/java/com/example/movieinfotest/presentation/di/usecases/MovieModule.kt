package com.example.movieinfotest.presentation.di.usecases

import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.usecases.MovieUseCase
import com.example.movieinfotest.presentation.di.repository.MovieRepModule
import dagger.Module
import dagger.Provides

@Module(includes = [MovieRepModule::class])
class MovieModule {

    @Provides
    fun getMovieUseCase(movieRepository: MovieRepository): MovieUseCase {
        return MovieUseCase(movieRepository)
    }
}
