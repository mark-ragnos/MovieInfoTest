package com.example.movieinfotest.presentation.ui.views

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.ui.composite.widgets.SimpleIconButton

@Composable
fun Toolbar(
    title: String,
    actions: @Composable RowScope.() -> Unit,
    navigationItem: (@Composable () -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = navigationItem,
        actions = actions,
    )
}

@Composable
fun ToolbarNavigationItem(
    goBack: () -> Unit
) {
    IconButton(onClick = { goBack() }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(id = R.string.cd_navigation_go_back)
        )
    }
}

@Composable
fun DefaultToolbarActions(
    darkModeOn: Boolean = false,
    changeDarkMode: (Boolean) -> Unit,
    isLogin: Boolean = false,
    doLogout: () -> Unit,
    doLogin: () -> Unit,
    visibleLogin: Boolean = true
) {
    val darkModeIcon =
        if (darkModeOn) painterResource(id = R.drawable.ic_light_mode)
        else painterResource(id = R.drawable.ic_dark_mode)

    SimpleIconButton(
        onCLick = { changeDarkMode(darkModeOn) },
        painter = darkModeIcon,
        contentDescription = stringResource(id = R.string.cd_change_darkmode)
    )
    if (visibleLogin) {
        val loginIcon =
            if (isLogin) painterResource(id = R.drawable.ic_logout)
            else painterResource(id = R.drawable.ic_login)

        SimpleIconButton(
            onCLick = {
                if (isLogin) {
                    doLogout()
                } else {
                    doLogin()
                }
            },
            painter = loginIcon,
            contentDescription = stringResource(id = R.string.cd_navigation_go_login)
        )
    }
}
