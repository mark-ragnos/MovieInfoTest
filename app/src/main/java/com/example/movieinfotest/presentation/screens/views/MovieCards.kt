package com.example.movieinfotest.presentation.screens.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.getYear

@Composable
fun ImageDescriptionMovie(
    movie: MovieDomain,
    onItemClick: (MovieDomain) -> Unit
) {
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onItemClick(movie) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "${movie.title} (${movie.releaseDate.getYear()})")
            Text(text = movie.overview, maxLines = 6)
        }
    }
}
