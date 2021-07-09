package com.example.movieinfotest.presentation.di.modules

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.FavoriteRepository
import com.example.movieinfotest.domain.repositories.IFavoriteRepository
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
object FavoriteModule {
    @Provides
    fun getUseCase(favoriteRepository: IFavoriteRepository): FavoriteMovieUseCase {
        return FavoriteMovieUseCase(favoriteRepository)
    }

    @Provides
    fun getRepository(apiHelper: ApiHelper, dbHelper: DbHelper): IFavoriteRepository {
        return FavoriteRepository(apiHelper, dbHelper)
    }
}
