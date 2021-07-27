package com.example.movieinfotest.presentation.ui.composite.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    onCLick: () -> Unit,
    painter: Painter,
    contentDescription: String
) = IconButton(
    onClick = onCLick,
    modifier = modifier,
    enabled = enabled
) {
    Icon(
        modifier = imageModifier,
        painter = painter,
        contentDescription = contentDescription,
        tint = tint
    )
}
