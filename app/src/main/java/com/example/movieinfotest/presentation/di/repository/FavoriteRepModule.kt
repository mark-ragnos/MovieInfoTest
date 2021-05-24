package com.example.movieinfotest.presentation.di.repository

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.FavoriteRepository
import com.example.movieinfotest.presentation.di.base.ApiModule
import com.example.movieinfotest.presentation.di.base.DbModule
import dagger.Module
import dagger.Provides

@Module(includes = [ApiModule::class, DbModule::class])
class FavoriteRepModule {

    @Provides
    fun getFavoriteRepository(apiHelper: ApiHelper, dbHelper: DbHelper): FavoriteRepository {
        return FavoriteRepository(apiHelper, dbHelper)
    }
}
