package com.example.movieinfotest.presentation.di.repos

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.GenreRepository
import com.example.movieinfotest.domain.repositories.IGenreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object GenreModule {
    @Provides
    fun provideGenreRepository(
        apiHelper: ApiHelper,
        dbHelper: DbHelper
    ): IGenreRepository {
        return GenreRepository(apiHelper, dbHelper)
    }
}
