package com.example.movieinfotest.presentation.di.usecases

import com.example.movieinfotest.data.repositories.GenreRepository
import com.example.movieinfotest.domain.usecases.GenreUseCase
import com.example.movieinfotest.presentation.di.repository.GenreRepModule
import dagger.Module
import dagger.Provides

@Module(includes = [GenreRepModule::class])
class GenreModule {

    @Provides
    fun getGenreUseCase(genreRepository: GenreRepository): GenreUseCase {
        return GenreUseCase(genreRepository)
    }
}
