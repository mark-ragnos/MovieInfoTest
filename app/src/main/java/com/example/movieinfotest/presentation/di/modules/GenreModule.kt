package com.example.movieinfotest.presentation.di.modules

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.GenreRepository
import com.example.movieinfotest.domain.repositories.IGenreRepository
import com.example.movieinfotest.domain.usecases.GenreUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
object GenreModule {
    @Provides
    fun getUseCase(genreRepository: IGenreRepository): GenreUseCase {
        return GenreUseCase(genreRepository)
    }

    @Provides
    fun getRepository(apiHelper: ApiHelper, dbHelper: DbHelper): IGenreRepository {
        return GenreRepository(apiHelper, dbHelper)
    }
}
