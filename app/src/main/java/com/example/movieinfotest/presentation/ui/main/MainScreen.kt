package com.example.movieinfotest.presentation.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.utils.navigation.NavigationItem
import com.example.movieinfotest.presentation.ui.utils.navigation.getTitle
import com.example.movieinfotest.presentation.ui.utils.navigation.isStart
import com.example.movieinfotest.presentation.ui.composite.navigation.DefaultToolbarActions
import com.example.movieinfotest.presentation.ui.views.MainBottomNavigationBar
import com.example.movieinfotest.presentation.ui.composite.navigation.Toolbar
import com.example.movieinfotest.presentation.ui.composite.navigation.ToolbarNavigationItem
import com.example.movieinfotest.presentation.ui.utils.navigation.isVisibleLoginButton

@Composable
fun MainScreen(
    activityViewModel: MainActivityViewModel,
    factory: AppViewModelFactory
) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = NavigationItem.fromRoute(backStackEntry.value?.destination?.route)

    val isLogin by activityViewModel.login.collectAsState()
    val isNight by activityViewModel.darkMode.collectAsState()

    Scaffold(
        topBar = {
            MainToolbar(
                currentScreen = currentScreen,
                goBack = { navController.popBackStack() },
                isLogin = isLogin,
                isDarkMode = isNight,
                login = { navController.navigate(NavigationItem.Login.name) },
                logout = { activityViewModel.logout() },
                darkModeClickEvent = { activityViewModel.changeDarkMode(it) }
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
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Favorite.name,
            modifier = Modifier.padding(it)
        ) {
            registerScreenNavigation(
                navController,
                backStackEntry,
                factory,
                activityViewModel
            )
        }
    }
}

@Composable
fun MainToolbar(
    currentScreen: NavigationItem,
    isVisible: Boolean = true,
    isDarkMode: Boolean = false,
    isLogin: Boolean = false,
    login: () -> Unit = {},
    logout: () -> Unit = {},
    darkModeClickEvent: (Boolean) -> Unit = {},
    goBack: () -> Unit = {}
) {
    if (isVisible) {
        Toolbar(
            title = stringResource(id = currentScreen.getTitle()),
            navigationItem = if (!currentScreen.isStart()) {
                { ToolbarNavigationItem(goBack = goBack) }
            } else null,
            actions = {
                DefaultToolbarActions(
                    darkModeOn = isDarkMode,
                    changeDarkMode = darkModeClickEvent,
                    isLogin = isLogin,
                    doLogin = login,
                    doLogout = logout,
                    visibleLogin = currentScreen.isVisibleLoginButton()
                )
            }
        )
    }
}
