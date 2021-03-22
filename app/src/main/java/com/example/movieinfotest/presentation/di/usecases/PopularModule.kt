package com.example.movieinfotest.presentation.di.usecases


import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.usecases.PopularMovieUseCase
import com.example.movieinfotest.presentation.di.repository.MovieRepModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module(includes = [MovieRepModule::class])
class PopularModule {

    @Provides
    fun getPopularUseCase(movieRepository: MovieRepository): PopularMovieUseCase {
        return PopularMovieUseCase(movieRepository)
    }
}