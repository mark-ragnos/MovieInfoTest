package com.example.movieinfotest.presentation.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.api.TheMovieDBApi
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.db.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule(
    private val application: Application
) {

    @Provides
    fun getDbHelper(db: MovieDatabase): DbHelper {
        return DbHelper(db)
    }

    @Provides
    fun getDatabase(context: Context, dbName: String): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, dbName).build()
    }

    @Provides
    fun getApiHelper(api: TheMovieDBApi): ApiHelper {
        return ApiHelper(api)
    }

    @Provides
    fun getApi(): TheMovieDBApi {
        return TheMovieDBApi.create()
    }

    @Provides
    fun getContext(): Context {
        return application
    }

    @Provides
    fun getDbName(): String {
        return "db_movie.db"
    }
}
