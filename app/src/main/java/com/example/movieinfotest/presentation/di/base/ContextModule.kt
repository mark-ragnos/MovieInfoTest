package com.example.movieinfotest.presentation.di.base

import android.app.Application
import android.content.Context
import com.example.movieinfotest.MovieApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule() {

    @Provides
    fun provideContext(): Context = MovieApp.getInstance()
}