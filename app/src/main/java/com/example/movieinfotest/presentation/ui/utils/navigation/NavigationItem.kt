package com.example.movieinfotest.presentation.ui.utils.navigation

enum class NavigationItem {
    Favorite,
    Popular,
    Random,
    Details,
    Actor,
    Registration,
    Login;

    companion object {
        fun fromRoute(route: String?) =
            when (route?.substringBefore("/")) {
                Favorite.name -> Favorite
                Popular.name -> Popular
                Random.name -> Random
                Details.name -> Details
                Actor.name -> Actor
                Registration.name -> Registration
                Login.name -> Login
                null -> Favorite
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
