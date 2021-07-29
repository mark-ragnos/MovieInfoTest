package com.example.movieinfotest.presentation.ui.composite.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.movieinfotest.utils.MAX_RATING

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    value: Float
) = CircularProgressIndicator(progress = value / MAX_RATING, modifier = modifier)

@Composable
fun PercentText(
    modifier: Modifier = Modifier,
    value: Float
) = Text(text = "${value.toInt()}%", modifier = modifier)

@Composable
fun RatingBarWithTextPercents(
    modifier: Modifier = Modifier,
    value: Float
) = Box(modifier = modifier) {
    RatingBar(value = value, modifier = Modifier.align(Alignment.Center))
    PercentText(value = value, modifier = Modifier.align(Alignment.Center))
}
