package com.example.movieinfotest.presentation.ui.popular

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.composite.pagination.PagingLazyColumn
import com.example.movieinfotest.presentation.ui.views.MovieCardItem

@Composable
fun PopularScreen(
    popularViewModel: PopularViewModel,
    goToDescription: (MovieDomain) -> Unit = {}
) {
    val movies = popularViewModel.movies.collectAsLazyPagingItems()

    PagingLazyColumn(items = movies) {
        MovieCardItem(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = true
                )
                .clickable { goToDescription(it) },
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
}
