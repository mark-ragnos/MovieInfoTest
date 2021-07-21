package com.example.movieinfotest.presentation.screens.views

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.movieinfotest.presentation.screens.navigation.NavigationItems
import com.example.movieinfotest.presentation.screens.navigation.getIconResId
import com.example.movieinfotest.presentation.screens.navigation.getLabelResId

@Composable
fun MainBottomNavigationBar(
    bottomScreens: List<NavigationItems>,
    currentScreen: NavigationItems,
    onScreenSelected: (NavigationItems) -> Unit,
    visible: Boolean
) {
    if (visible) {
        BottomNavigation {
            bottomScreens.forEach { screen ->
                BottomNavigationItem(
                    selected = screen == currentScreen,
                    icon = {
                        BottomIcon(screen)
                    },
                    label = {
                        TextItem(screen)
                    },
                    alwaysShowLabel = false,
                    onClick = { onScreenSelected(screen) }
                )
            }
        }
    }
}

@Composable
fun BottomIcon(
    screen: NavigationItems
) {
    Icon(
        painter = painterResource(screen.getIconResId()),
        contentDescription = screen.name
    )
}

@Composable
fun TextItem(
    screen: NavigationItems
) {
    Text(
        text = stringResource(screen.getLabelResId())
    )
}
