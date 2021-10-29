package com.example.movieinfotest.presentation.di.base

import android.content.Context
import androidx.room.Room
import com.example.movieinfotest.BuildConfig
import com.example.movieinfotest.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        dbName: String
    ): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, dbName).build()
    }

    @Provides
    fun provideDatabaseName(): String {
        return BuildConfig.DB_NAME
    }
}
