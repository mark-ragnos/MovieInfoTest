package com.example.movieinfotest.domain.entities.actor

fun CrewDomain.asCast(): CastDomain {
    return CastDomain(
        id,
        name,
        job,
        profilePath,
        gender
    )
}

fun List<CrewDomain>.asCast(): List<CastDomain> {
    return map {
        it.asCast()
    }
}
