package com.example.movieinfotest.presentation.screens.utils.navigation

import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.movie.MovieDomain

enum class NavigationItem {
    Favorite,
    Popular,
    Random,
    Details,
    Actor;

    companion object {
        fun fromRoute(route: String?) =
            when (route?.substringBefore("/")) {
                Favorite.name -> Favorite
                Popular.name -> Popular
                Random.name -> Random
                Details.name -> Details
                Actor.name -> Actor
                null -> Favorite
                else -> throw IllegalArgumentException("Route $route is not recognized.")

            }
    }
}
