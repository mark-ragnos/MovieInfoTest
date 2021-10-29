package com.example.movieinfotest.presentation.di.repos

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.FavoriteRepository
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object FavoriteModule {
    @Provides
    fun provideFavoriteRepository(
        apiHelper: ApiHelper,
        dbHelper: DbHelper
    ): IFavoriteRepository {
        return FavoriteRepository(apiHelper, dbHelper)
    }
}
