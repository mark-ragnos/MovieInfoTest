package com.example.movieinfotest.presentation.di.modules

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.db.DbHelper
import com.example.movieinfotest.data.repositories.MovieRepository
import com.example.movieinfotest.domain.repositories.IMovieRepository
import com.example.movieinfotest.domain.usecases.MovieUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
object MovieModule {
    @Provides
    fun getUseCase(movieRepository: IMovieRepository): MovieUseCase {
        return MovieUseCase(movieRepository)
    }

    @Provides
    fun getRepository(apiHelper: ApiHelper, dbHelper: DbHelper): IMovieRepository {
        return MovieRepository(apiHelper, dbHelper)
    }
}
