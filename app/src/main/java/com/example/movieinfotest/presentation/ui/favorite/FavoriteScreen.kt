package com.example.movieinfotest.presentation.ui.favorite

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.composite.pagination.PagingLazyColumn
import com.example.movieinfotest.presentation.ui.views.PopularMovieCard

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    moveToDetails: (MovieDomain) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    PagingLazyColumn(items = movies) {
        PopularMovieCard(
            movie = it,
            onItemClick = { movie ->
                moveToDetails(movie)
            }
        )
    }
}
