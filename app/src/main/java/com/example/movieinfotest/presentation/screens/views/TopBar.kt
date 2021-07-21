package com.example.movieinfotest.presentation.screens.views

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.movieinfotest.R

@Composable
fun Toolbar(
    title: String,
    backVisible: Boolean = false,
    goBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = actions,
    )
}

@Composable
fun RowScope.DefaultToolbarActions(
    darkModeOn: Boolean = false,
    changeDarkMode: (Boolean) -> Unit,
    isLogin: Boolean = false,
    logout: () -> Unit,
    login: () -> Unit,
    visibleDarkMode: Boolean = true,
    visibleLogin: Boolean = true
) {
    if (visibleDarkMode) {
        val darkModeIcon =
            if (darkModeOn) painterResource(id = R.drawable.ic_light_mode)
            else painterResource(id = R.drawable.ic_dark_mode)

        IconButton(onClick = { changeDarkMode(darkModeOn) }) {
            Icon(painter = darkModeIcon, contentDescription = "TODO")
        }
    }

    if (visibleLogin) {
        val loginIcon =
            if (isLogin) painterResource(id = R.drawable.ic_logout)
            else painterResource(id = R.drawable.ic_login)

        IconButton(onClick = {
            if (isLogin) {
                logout()
            } else {
                login()
            }
        }) {
            Icon(painter = loginIcon, contentDescription = "TODO")
        }
    }
}
