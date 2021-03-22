package com.example.movieinfotest.presentation.di.usecases


import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.usecases.MovieInfoUseCase
import com.example.movieinfotest.presentation.di.repository.MovieRepModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module(includes = [MovieRepModule::class])
class MovieInfoModule {

    @Provides
    fun getMovieInfoUseCase(movieRepository: MovieRepository): MovieInfoUseCase {
        return MovieInfoUseCase(movieRepository)
    }
}