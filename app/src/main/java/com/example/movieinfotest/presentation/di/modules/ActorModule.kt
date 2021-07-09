package com.example.movieinfotest.presentation.di.modules

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.data.repositories.ActorRepository
import com.example.movieinfotest.domain.repositories.IActorRepository
import com.example.movieinfotest.domain.usecases.ActorUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
object ActorModule {
    @Provides
    fun getUseCase(actorRepository: IActorRepository): ActorUseCase {
        return ActorUseCase(actorRepository)
    }

    @Provides
    fun getRepository(apiHelper: ApiHelper): IActorRepository {
        return ActorRepository(apiHelper)
    }
}
