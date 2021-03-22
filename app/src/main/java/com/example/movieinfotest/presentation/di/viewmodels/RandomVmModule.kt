package com.example.movieinfotest.presentation.di.viewmodels

import com.example.movieinfotest.domain.usecases.RandomMovieUseCase
import com.example.movieinfotest.presentation.di.usecases.RandomModule
import com.example.movieinfotest.presentation.ui.random.RandomViewModel
import dagger.Module
import dagger.Provides


@Module(includes = [RandomModule::class])
class RandomVmModule {

    @Provides
    fun getViewModel(randomMovieUseCase: RandomMovieUseCase): RandomViewModel {
        return RandomViewModel(randomMovieUseCase)
    }
}