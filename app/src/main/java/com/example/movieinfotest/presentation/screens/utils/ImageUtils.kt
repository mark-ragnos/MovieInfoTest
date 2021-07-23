package com.example.movieinfotest.presentation.screens.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ImageSize(
    val width: Dp,
    val height: Dp
)

val POSTER_IMAGE_SIZE = ImageSize(77.dp, 114.dp)
val POSTER_IMAGE_SIZE_BIG = ImageSize(115.dp, 171.dp)
