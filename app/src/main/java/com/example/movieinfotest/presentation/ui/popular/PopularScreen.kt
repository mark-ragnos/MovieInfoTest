package com.example.movieinfotest.presentation.ui.popular

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.composite.pagination.PagingLazyColumn
import com.example.movieinfotest.presentation.ui.views.PopularMovieCard

@Composable
fun PopularScreen(
    popularViewModel: PopularViewModel,
    goToDescription: (MovieDomain) -> Unit = {}
) {
    val movies = popularViewModel.movies.collectAsLazyPagingItems()

    PagingLazyColumn(items = movies) {
        PopularMovieCard(
            movie = it,
            visibleFavorite = true,
            isFavorite = { movie -> popularViewModel.isFavorite(movie.id) },
            onFavoriteClick = { movie, isFavorite ->
                if (isFavorite) {
                    popularViewModel.removeFromFavorite(movie)
                } else {
                    popularViewModel.saveInFavorite(movie)
                }
            },
            onItemClick = goToDescription
        )
    }
}
