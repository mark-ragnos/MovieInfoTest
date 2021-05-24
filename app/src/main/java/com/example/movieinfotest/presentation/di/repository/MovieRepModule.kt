package com.example.movieinfotest.presentation.di.repository

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.presentation.di.base.ApiModule
import com.example.movieinfotest.presentation.di.base.DbModule
import dagger.Module
import dagger.Provides

@Module(includes = [ApiModule::class, DbModule::class])
class MovieRepModule {

    @Provides
    fun getMovieRepository(apiHelper: ApiHelper, dbHelper: DbHelper): MovieRepository {
        return MovieRepository(apiHelper, dbHelper)
    }
}
