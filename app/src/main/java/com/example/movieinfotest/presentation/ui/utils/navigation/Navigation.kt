package com.example.movieinfotest.presentation.ui.utils.navigation

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
