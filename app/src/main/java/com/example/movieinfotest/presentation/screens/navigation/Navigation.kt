package com.example.movieinfotest.presentation.screens.navigation

import com.example.movieinfotest.R

enum class NavigationItems {
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

fun NavigationItems.getIconResId(): Int = when (this) {
    NavigationItems.Favorite -> {
        R.drawable.ic_favorite
    }
    NavigationItems.Popular -> {
        R.drawable.ic_popular
    }
    NavigationItems.Random -> {
        R.drawable.ic_dice
    }
    else -> {
        throw IllegalArgumentException("This item is not bottomNavigation item")
    }
}

fun NavigationItems.getLabelResId(): Int = when (this) {
    NavigationItems.Favorite -> {
        R.string.favorite
    }
    NavigationItems.Popular -> {
        R.string.popular
    }
    NavigationItems.Random -> {
        R.string.random
    }
    else -> {
        throw IllegalArgumentException("This item is not bottomNavigation item")
    }
}
