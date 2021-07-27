package com.example.movieinfotest.presentation.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GenreList(
    modifier: Modifier = Modifier,
    genres: List<GenreDomain>
) {
    FlowRow(
        modifier = modifier,
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 4.dp,
        crossAxisAlignment = FlowCrossAxisAlignment.Center
    ) {
        genres.forEach {
            GenreItem(genre = it)
        }
    }
}

@Composable
fun GenreItem(
    genre: GenreDomain
) {
    Box(
        modifier = Modifier.border(
            1.dp,
            MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Text(text = genre.name, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
    }
}
