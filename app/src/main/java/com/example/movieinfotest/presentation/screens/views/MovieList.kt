package com.example.movieinfotest.presentation.screens.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.domain.entities.movie.MovieDomain

@Composable
fun MovieList(
    movies: List<MovieDomain>,
    displayItem: @Composable (MovieDomain) -> Unit,
    itemPaddingHorizontal: Dp = 8.dp,
    decorator: @Composable (MovieDomain) -> Unit = {
        Spacer(Modifier.padding(4.dp))
    }
) {
    LazyColumn {

    }
}

@Composable
fun MovieItemFavorite(
    movieDomain: MovieDomain,
    onItemClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.clickable {
            onItemClick()
        },
        elevation = 2.dp,
    ) {

    }
}
