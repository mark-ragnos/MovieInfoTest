package com.example.movieinfotest.presentation.ui.main

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.details.DetailsScreen
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.details.actors.ActorScreen
import com.example.movieinfotest.presentation.ui.details.actors.ActorViewModel
import com.example.movieinfotest.presentation.ui.favorite.FavoriteScreen
import com.example.movieinfotest.presentation.ui.favorite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.popular.PopularScreen
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.random.RandomScreen
import com.example.movieinfotest.presentation.ui.random.RandomViewModel
import com.example.movieinfotest.presentation.ui.signIn.LoginScreen
import com.example.movieinfotest.presentation.ui.signIn.RegistrationScreen
import com.example.movieinfotest.presentation.ui.utils.navigation.NavigationItem

fun NavGraphBuilder.registerScreenNavigation(
    navController: NavController,
    backStackEntry: NavBackStackEntry?,
    factory: AppViewModelFactory,
    activityViewModel: MainActivityViewModel
) {
    registerFavoriteDestination(navController, factory)
    registerPopularDestination(navController, factory, activityViewModel)
    registerRandomDestination(navController, factory)
    registerDetailsDestination(navController, backStackEntry, factory)
    registerActorDestination(backStackEntry, factory)
    registerRegistrationDestination(navController, activityViewModel)
    registerLoginDestination(navController, activityViewModel)
}

private fun NavGraphBuilder.registerLoginDestination(
    navController: NavController,
    activityViewModel: MainActivityViewModel
) = composable(
    route = NavigationItem.Login.name
) {
    LoginScreen(
        activityViewModel = activityViewModel,
        goToRegistration = {
            navController.navigate(NavigationItem.Registration.name) {
                launchSingleTop = true
            }
        },
        goBack = {
            navController.popBackStack()
        }
    )
}

private fun NavGraphBuilder.registerRegistrationDestination(
    navController: NavController,
    activityViewModel: MainActivityViewModel
) = composable(
    route = NavigationItem.Registration.name
) {
    RegistrationScreen(
        activityViewModel = activityViewModel,
        goToLogin = {
            navController.popBackStack()
        }
    )
}

private fun NavGraphBuilder.registerActorDestination(
    backStackEntry: NavBackStackEntry?,
    factory: AppViewModelFactory
) = composable(
    route = "${NavigationItem.Actor.name}/{actorId}",
    arguments = listOf(navArgument("actorId") { type = NavType.IntType })
) {
    val actorViewModel: ActorViewModel = viewModel(factory = factory)

    ActorScreen(
        actorId = backStackEntry?.arguments?.getInt("actorId") ?: 0,
        actorViewModel = actorViewModel
    )
}

private fun NavGraphBuilder.registerDetailsDestination(
    navController: NavController,
    backStackEntry: NavBackStackEntry?,
    factory: AppViewModelFactory
) = composable(
    route = "${NavigationItem.Details.name}/{movieId}",
    arguments = listOf(navArgument("movieId") { type = NavType.IntType })
) {
    val detailsViewModel: DetailsViewModel = viewModel(
        factory = factory
    )
    DetailsScreen(
        detailsViewModel = detailsViewModel,
        movieId = backStackEntry?.arguments?.getInt("movieId") ?: 0,
        moveToActor = { id ->
            navController.navigate("${NavigationItem.Actor.name}/${id}")
        }
    )
}

private fun NavGraphBuilder.registerRandomDestination(
    navController: NavController,
    factory: AppViewModelFactory
) = composable(
    route = NavigationItem.Random.name
) {
    val randomViewModel: RandomViewModel = viewModel(
        factory = factory
    )

    RandomScreen(
        randomViewModel = randomViewModel,
        goToDescription = { movie ->
            navController.navigate("${NavigationItem.Details.name}/${movie.id}")
        })
}

private fun NavGraphBuilder.registerPopularDestination(
    navController: NavController,
    factory: AppViewModelFactory,
    activityViewModel: MainActivityViewModel
) = composable(
    route = NavigationItem.Popular.name
) {
    val popularViewModel: PopularViewModel = viewModel(
        factory = factory
    )
    PopularScreen(
        popularViewModel,
        activityViewModel
    ) { movie -> navController.navigate("${NavigationItem.Details.name}/${movie.id}") }
}

private fun NavGraphBuilder.registerFavoriteDestination(
    navController: NavController,
    factory: AppViewModelFactory
) = composable(
    route = NavigationItem.Favorite.name
) {
    val favoriteViewModel: FavoriteViewModel = viewModel(
        factory = factory
    )
    FavoriteScreen(
        favoriteViewModel = favoriteViewModel,
        goToDetails = { movie ->
            navController.navigate("${NavigationItem.Details.name}/${movie.id}")
        }
    )
}
