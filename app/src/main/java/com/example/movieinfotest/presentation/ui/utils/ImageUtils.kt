package com.example.movieinfotest.presentation.ui.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ImageSize(
    val width: Dp,
    val height: Dp
)

val POSTER_IMAGE_SIZE = ImageSize(77.dp, 114.dp)
val POSTER_IMAGE_SIZE_BIG = ImageSize(115.dp, 171.dp)
val POSTER_IMAGE_SIZE_DETAILS = ImageSize(100.dp, 150.dp)

val PROFILE_FACE = ImageSize(100.dp, (110.8333f).dp)

val RATIO_POSTER = 1.5f
val RATIO_BACKDROP = 1.77777f
