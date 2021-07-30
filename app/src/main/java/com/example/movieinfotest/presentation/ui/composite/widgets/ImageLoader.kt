package com.example.movieinfotest.presentation.ui.composite.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import com.example.movieinfotest.R
import com.example.movieinfotest.utils.FLAG_POSTER_W154

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    path: String?,
    imageResolution: String = FLAG_POSTER_W154,
    imageUri: String = "https://www.themoviedb.org/t/p/$imageResolution",
    @DrawableRes placeholder: Int = R.drawable.ic_placeholder_movie,
    contentDescription: String
) = Image(
    painter = rememberImagePainter(data = imageUri + path) {
        placeholder(placeholder)
        error(placeholder)
    },
    contentDescription = contentDescription,
    modifier = modifier
)
