package com.example.movieinfotest.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.screens.favorite.FavoriteScreen
import com.example.movieinfotest.presentation.screens.navigation.NavigationItems
import com.example.movieinfotest.presentation.screens.random.RandomScreen
import com.example.movieinfotest.presentation.screens.views.MainBottomNavigationBar
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel

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
    val currentScreen = NavigationItems.fromRoute(backStackEntry.value?.destination?.route)

    Scaffold(
        bottomBar = {
            MainBottomNavigationBar(
                bottomScreens = listOf(
                    NavigationItems.Favorite,
                    NavigationItems.Popular,
                    NavigationItems.Random
                ),
                currentScreen = currentScreen,
                onScreenSelected = { navController.navigate(it.name) },
                visible = bottomVisible
            )
        }
    )
    {
        NavHost(
            navController = navController,
            startDestination = NavigationItems.Favorite.name,
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = NavigationItems.Favorite.name
            ) {
                val favoriteViewModel: FavoriteViewModel = viewModel(
                    factory = factory
                )
                FavoriteScreen(
                    mainViewModel = activityViewModel,
                    viewModel = favoriteViewModel,
                    moveToDetails = {

                    }
                )
            }

            composable(
                route = NavigationItems.Popular.name
            ) {

            }

            composable(
                route = NavigationItems.Random.name
            ) {
                val randomViewModel: RandomViewModel = viewModel(
                    factory = factory
                )
                RandomScreen(activityViewModel = activityViewModel, viewModel = randomViewModel)
            }

            composable(
                route = NavigationItems.Details.name
            ) {

            }

            composable(
                route = NavigationItems.Actor.name
            ) {

            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(MainActivityViewModel())
}
