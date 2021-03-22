package com.example.movieinfotest.presentation.di.repository

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.GenreRepository
import com.example.movieinfotest.presentation.di.base.ApiModule
import com.example.movieinfotest.presentation.di.base.DbModule
import dagger.Module
import dagger.Provides


@Module(includes = [ApiModule::class, DbModule::class])
class GenreRepModule {

    @Provides
    fun getGenreRepository(apiHelper: ApiHelper, dbHelper: DbHelper): GenreRepository {
        return GenreRepository(apiHelper, dbHelper)
    }
}