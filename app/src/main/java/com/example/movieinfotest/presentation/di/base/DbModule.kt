package com.example.movieinfotest.presentation.di.base

import android.content.Context
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.db.MovieDatabase
import dagger.Module
import dagger.Provides


@Module(includes = [ContextModule::class])
class DbModule {

    @Provides
    fun getDbHelper(context: Context): DbHelper {
        return DbHelper(MovieDatabase.create(context))
    }
}