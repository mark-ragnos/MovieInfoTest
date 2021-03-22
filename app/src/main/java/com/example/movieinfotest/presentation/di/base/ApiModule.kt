package com.example.movieinfotest.presentation.di.base


import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.api.TheMovieDBApi
import dagger.Module
import dagger.Provides


@Module
class ApiModule {


    @Provides
    fun getApiHelper(): ApiHelper {
        return ApiHelper(TheMovieDBApi.create())
    }
}