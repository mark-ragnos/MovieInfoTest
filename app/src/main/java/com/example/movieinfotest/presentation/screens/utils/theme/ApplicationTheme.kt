package com.example.movieinfotest.presentation.screens.utils.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.movieinfotest.R

@Composable
fun MovieDBTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) darkColors else lightColors,
        content = content
    )
}

private val darkColors = darkColors(
    primary = DARK_BLUE_700,
    primaryVariant = DARK_BLUE_900,
    secondary = LIGHT_BLUE_400,
    secondaryVariant = LIGHT_BLUE_700,
)

private val lightColors = lightColors(
    primary = DARK_BLUE_700,
    primaryVariant = DARK_BLUE_900,
    secondary = LIGHT_BLUE_400,
    secondaryVariant = LIGHT_BLUE_700,

)