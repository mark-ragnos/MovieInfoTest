package com.example.movieinfotest.presentation.di.repos

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.repositories.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MovieModule {
    @Provides
    fun provideMovieRepository(
        apiHelper: ApiHelper,
        dbHelper: DbHelper
    ): IMovieRepository {
        return MovieRepository(apiHelper, dbHelper)
    }
}
