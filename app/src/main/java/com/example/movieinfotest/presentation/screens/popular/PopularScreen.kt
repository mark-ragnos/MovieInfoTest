package com.example.movieinfotest.presentation.screens.popular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfotest.presentation.screens.views.LazyMovieList
import com.example.movieinfotest.presentation.screens.views.PopularMovieCard
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel

@Composable
fun PopularScreen(
    popularViewModel: PopularViewModel
) {
    val movies = popularViewModel.movies.collectAsLazyPagingItems()

    LazyMovieList(
        movies = movies,
        displayItem = {
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
                }
            )
        }
    )
}
