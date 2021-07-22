package com.example.movieinfotest.presentation.screens.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.movieinfotest.domain.entities.movie.MovieDomain

@Composable
fun LazyMovieList(
    movies: LazyPagingItems<MovieDomain>,
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
fun MovieList(
    movies: List<MovieDomain>,
    displayItem: @Composable (MovieDomain) -> Unit,
    itemPaddingHorizontal: Dp = 8.dp,
    decorator: @Composable (MovieDomain) -> Unit = {
        Spacer(Modifier.padding(4.dp))
    }
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = movies) { item: MovieDomain ->
            decorator(item)
            displayItem(item)
            decorator(item)
        }
    }
}
