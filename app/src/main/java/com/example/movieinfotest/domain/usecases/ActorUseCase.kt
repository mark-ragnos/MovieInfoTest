package com.example.movieinfotest.domain.usecases

import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain
import com.example.movieinfotest.domain.repositories.IActorRepository
import javax.inject.Inject

class ActorUseCase @Inject constructor(
    private val actorRepository: IActorRepository
) {
    suspend fun getActorInfo(actorId: Int): ActorInfoDomain? {
        return actorRepository.getActorInfo(actorId)
    }
}
