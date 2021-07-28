package com.example.movieinfotest.presentation.ui.utils.navigation

import com.example.movieinfotest.R

fun NavigationItem.getIconResId(): Int = when (this) {
    NavigationItem.Favorite -> {
        R.drawable.ic_favorite
    }
    NavigationItem.Popular -> {
        R.drawable.ic_popular
    }
    NavigationItem.Random -> {
        R.drawable.ic_dice
    }
    else -> {
        throw IllegalArgumentException("This item is not bottomNavigation item")
    }
}

fun NavigationItem.getLabelResId(): Int = when (this) {
    NavigationItem.Favorite -> {
        R.string.favorite
    }
    NavigationItem.Popular -> {
        R.string.popular
    }
    NavigationItem.Random -> {
        R.string.random
    }
    else -> {
        throw IllegalArgumentException("This item is not bottomNavigation item")
    }
}

fun NavigationItem.getTitle(): Int = when (this) {
    NavigationItem.Favorite -> {
        R.string.favorite_title
    }
    NavigationItem.Popular -> {
        R.string.popular_title
    }
    NavigationItem.Random -> {
        R.string.random_title
    }
    NavigationItem.Details -> {
        R.string.details_title
    }
    NavigationItem.Actor -> {
        R.string.actorInfo_title
    }
    NavigationItem.SignIn -> {
        R.string.signin_title
    }
    NavigationItem.LogIn -> {
        R.string.login_title
    }
    else -> {
        throw IllegalArgumentException("This item is not bottomNavigation item")
    }
}

fun NavigationItem.isStart() = when (this) {
    NavigationItem.Favorite -> {
        true
    }
    NavigationItem.Popular -> {
        true
    }
    NavigationItem.Random -> {
        true
    }
    else -> {
        false
    }
}
