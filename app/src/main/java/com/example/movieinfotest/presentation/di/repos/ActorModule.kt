package com.example.movieinfotest.presentation.di.repos

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.repositories.ActorRepository
import com.example.movieinfotest.domain.repositories.IActorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ActorModule {
    @Provides
    fun provideActorRepos(apiHelper: ApiHelper): IActorRepository {
        return ActorRepository(apiHelper)
    }
}
