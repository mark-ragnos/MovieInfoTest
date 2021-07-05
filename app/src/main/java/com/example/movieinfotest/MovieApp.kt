package com.example.movieinfotest

import android.app.Application
import android.content.Context
import com.example.movieinfotest.presentation.di.AppComponent
import com.example.movieinfotest.presentation.di.DaggerAppComponent
import com.example.movieinfotest.presentation.di.modules.AppModule

class MovieApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(
                AppModule(
                    this
                )
            ).build()
//        appComponent = DaggerAppComponent.create()
    }
//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//        database = Room.databaseBuilder(instance, MovieDatabase::class.java, "db_movie.db").build()
//    }
//
//    fun getDatabase(): MovieDatabase {
//        return database
//    }
//
//    companion object {
//        private lateinit var database: MovieDatabase
//        private lateinit var instance: MovieApp
//
//        fun getInstance(): MovieApp {
//            return instance
//        }
//    }
}

fun Context.getComponent(): AppComponent {
    return if (this is MovieApp) {
        appComponent
    } else {
        applicationContext.getComponent()
    }
}
