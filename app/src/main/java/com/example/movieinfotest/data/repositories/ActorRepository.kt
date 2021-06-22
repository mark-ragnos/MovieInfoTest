package com.example.movieinfotest.data.repositories

import com.example.movieinfotest.data.api.ApiHelper
import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain
import com.example.movieinfotest.domain.repositories.IActorRepository
import com.example.movieinfotest.utils.converters.toActorInfoDomain

class ActorRepository(private val apiHelper: ApiHelper) : IActorRepository {
    override suspend fun getActorInfo(actorId: Int): ActorInfoDomain? {
        return apiHelper.getActorInfo(actorId)?.toActorInfoDomain()
    }
}
