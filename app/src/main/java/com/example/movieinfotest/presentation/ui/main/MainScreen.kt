package com.example.movieinfotest.presentation.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.details.DetailsScreen
import com.example.movieinfotest.presentation.ui.favorite.FavoriteScreen
import com.example.movieinfotest.presentation.ui.popular.PopularScreen
import com.example.movieinfotest.presentation.ui.random.RandomScreen
import com.example.movieinfotest.presentation.ui.random.RandomViewModel
import com.example.movieinfotest.presentation.ui.utils.navigation.NavigationItem
import com.example.movieinfotest.presentation.ui.utils.navigation.getTitle
import com.example.movieinfotest.presentation.ui.utils.navigation.isStart
import com.example.movieinfotest.presentation.ui.views.DefaultToolbarActions
import com.example.movieinfotest.presentation.ui.views.MainBottomNavigationBar
import com.example.movieinfotest.presentation.ui.views.Toolbar
import com.example.movieinfotest.presentation.ui.views.ToolbarNavigationItem
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel
import com.example.movieinfotest.presentation.ui.details.actors.ActorViewModel
import com.example.movieinfotest.presentation.ui.favorite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.details.actors.ActorScreen
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel
import com.example.movieinfotest.presentation.ui.signIn.SignInScreen

@Composable
fun MainScreen(
    activityViewModel: MainActivityViewModel,
    factory: AppViewModelFactory? = null
) {
    val (bottomVisible, setBottomVisible) = remember {
        mutableStateOf(true)
    }

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = NavigationItem.fromRoute(backStackEntry.value?.destination?.route)

    Scaffold(
        topBar = {
            MainToolbar(
                activityViewModel = activityViewModel,
                currentScreen = currentScreen,
                goBack = {
                    navController.popBackStack()
                },
                goToLogin = {
                    navController.navigate(NavigationItem.SignIn.name)
                }
            )
        },
        bottomBar = {
            MainBottomNavigationBar(
                bottomScreens = listOf(
                    NavigationItem.Favorite,
                    NavigationItem.Popular,
                    NavigationItem.Random
                ),
                currentScreen = currentScreen,
                onScreenSelected = {
                    navController.navigate(it.name) {
                        popUpTo(NavigationItem.Favorite.name)
                        launchSingleTop = true
                    }
                },
                visible = bottomVisible
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Favorite.name,
            modifier = Modifier.padding(it)
        ) {
            composable(
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

            composable(
                route = NavigationItem.Popular.name
            ) {
                val popularViewModel: PopularViewModel = viewModel(
                    factory = factory
                )
                PopularScreen(
                    popularViewModel
                ) { movie -> navController.navigate("${NavigationItem.Details.name}/${movie.id}") }
            }

            composable(
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

            composable(
                route = "${NavigationItem.Details.name}/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) {
                val detailsViewModel: DetailsViewModel = viewModel(
                    factory = factory
                )
                DetailsScreen(
                    detailsViewModel = detailsViewModel,
                    movieId = backStackEntry.value?.arguments?.getInt("movieId") ?: 0,
                    moveToActor = { id ->
                        navController.navigate("${NavigationItem.Actor.name}/${id}")
                    }
                )
            }

            composable(
                route = "${NavigationItem.Actor.name}/{actorId}",
                arguments = listOf(navArgument("actorId") { type = NavType.IntType })
            ) {
                val actorViewModel: ActorViewModel = viewModel(factory = factory)

                ActorScreen(
                    actorId = backStackEntry.value?.arguments?.getInt("actorId") ?: 0,
                    actorViewModel = actorViewModel
                )
            }

            composable(
                route = NavigationItem.SignIn.name
            ) {
                SignInScreen(mainActivityViewModel = activityViewModel)
            }

            composable(
                route = NavigationItem.LogIn.name
            ) {
                SignInScreen(mainActivityViewModel = activityViewModel)
            }
        }
    }
}

@Composable
fun MainToolbar(
    activityViewModel: MainActivityViewModel,
    currentScreen: NavigationItem,
    goBack: () -> Unit = {},
    goToLogin: () -> Unit = {},
    visible: Boolean = true,
) {
    val darkMode by activityViewModel.darkMode.collectAsState()
    val login by activityViewModel.login.collectAsState()
    if (visible) {
        Toolbar(
            title = stringResource(id = currentScreen.getTitle()),
            navigationItem = if (!currentScreen.isStart()) {
                { ToolbarNavigationItem(goBack = goBack) }
            } else null,
            actions = {
                DefaultToolbarActions(
                    darkModeOn = darkMode,
                    changeDarkMode = { activityViewModel.changeDarkMode(it) },
                    isLogin = login,
                    login = goToLogin,
                    logout = { activityViewModel.logout() }
                )
            }
        )
    }
}
