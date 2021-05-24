package com.example.movieinfotest.presentation.di.base

import android.content.Context
import com.example.movieinfotest.MovieApp
import dagger.Module
import dagger.Provides

@Module
class ContextModule {

    @Provides
    fun provideContext(): Context = MovieApp.getInstance()
}
