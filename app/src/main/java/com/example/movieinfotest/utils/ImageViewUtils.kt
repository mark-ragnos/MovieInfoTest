package com.example.movieinfotest.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val FLAG_POSTER_W154 = "w154"
const val FLAG_POSTER_W185 = "w185"

const val FLAG_BACKDROP_W300 = "w300"
const val FLAG_BACKDROP_W780 = "w780"

const val FLAG_PROFILE_W154 = "w154"
const val FLAG_PROFILE_W185 = "w185"
const val FLAG_PROFILE_FACE = "w240_and_h266_face"

data class ImageSize(
    val width: Dp,
    val height: Dp
)

val POSTER_IMAGE_SIZE = ImageSize(77.dp, 114.dp)
val POSTER_IMAGE_SIZE_BIG = ImageSize(115.dp, 171.dp)
val POSTER_IMAGE_SIZE_DETAILS = ImageSize(100.dp, 150.dp)

val PROFILE_FACE = ImageSize(100.dp, (110.8333f).dp)

const val RATIO_POSTER = 1.5f
const val RATIO_BACKDROP = 1.77777f
