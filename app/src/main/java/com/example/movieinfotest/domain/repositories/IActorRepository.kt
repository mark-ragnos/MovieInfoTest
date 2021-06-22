package com.example.movieinfotest.domain.repositories

import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain

interface IActorRepository {
    suspend fun getActorInfo(actorId: Int): ActorInfoDomain?
}
