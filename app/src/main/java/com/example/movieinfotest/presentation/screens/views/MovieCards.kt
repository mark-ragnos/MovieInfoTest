package com.example.movieinfotest.presentation.screens.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.screens.utils.POSTER_IMAGE_SIZE
import com.example.movieinfotest.presentation.screens.utils.POSTER_IMAGE_SIZE_BIG
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
        Row() {
            ImageLoader(path = movie.posterPath, imageSize = POSTER_IMAGE_SIZE_BIG)

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "${movie.title} (${movie.releaseDate.getYear()})")
                Text(text = movie.overview, maxLines = 6)
            }
        }
    }
}

@Composable
fun PopularMovieCard(
    movie: MovieDomain,
    onItemClick: (MovieDomain) -> Unit = {},
    isFavorite: suspend (MovieDomain) -> Boolean = { false },
    onFavoriteClick: (MovieDomain, Boolean) -> Unit = { _, _ -> },
    visibleFavorite: Boolean = false
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(movie)
            },
        elevation = 2.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row {
            ImageLoader(
                path = movie.posterPath,
                imageSize = POSTER_IMAGE_SIZE
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f)
            ) {
                Text(text = movie.title, maxLines = 2)
                Text(text = movie.voteAverage.toString())
            }

            if (visibleFavorite) {
                FavoriteButton(
                    movie = movie,
                    isFavorite = isFavorite,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}