package com.example.movieinfotest.utils.converters

import com.example.movieinfotest.data.entities.api.actor.ActorInfo
import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain

fun ActorInfo.toActorInfoDomain(): ActorInfoDomain {
    return ActorInfoDomain(
        birthday,
        knownForDepartment,
        deathDay,
        id,
        name,
        alsoKnownAs,
        gender,
        biography,
        popularity,
        placeOfBirth,
        profilePath,
        adult,
        homePage
    )
}
