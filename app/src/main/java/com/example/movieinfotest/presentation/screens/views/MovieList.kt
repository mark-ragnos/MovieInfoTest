package com.example.movieinfotest.presentation.screens.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.movieinfotest.domain.entities.movie.MovieDomain

@Composable
fun MovieList(
    movies: List<MovieDomain>,
    displayItem: @Composable (MovieDomain) -> Unit,
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

@Composable
fun LazyMovieList(
    movies: LazyPagingItems<MovieDomain>,
    displayItem: @Composable (MovieDomain) -> Unit,
    decorator: @Composable (MovieDomain) -> Unit = {
        Spacer(Modifier.padding(3.dp))
    }
) {
    LazyColumn {
        items(
            lazyPagingItems = movies
        ) { item ->
            if (item != null) {
                decorator(item)
                displayItem(item)
                decorator(item)
            }
        }
        observePagingState(items = movies)
    }
}
