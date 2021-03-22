package com.example.movieinfotest.presentation.di.usecases

import com.example.movieinfotest.data.repositories.FavoriteRepository
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.presentation.di.repository.FavoriteRepModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module(includes =  [FavoriteRepModule::class])
class FavoriteModule {

    @Provides
    fun getFavoriteUseCase(favoriteRepository: FavoriteRepository): FavoriteMovieUseCase {
        return FavoriteMovieUseCase(favoriteRepository)
    }

}