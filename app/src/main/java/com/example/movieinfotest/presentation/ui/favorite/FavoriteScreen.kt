package com.example.movieinfotest.presentation.ui.favorite

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
fun FavoriteScreen(
    favoriteViewModel: FavoriteViewModel,
    goToDetails: (MovieDomain) -> Unit
) {
    val movies = favoriteViewModel.movies.collectAsLazyPagingItems()

    PagingLazyColumn(
        items = movies,
    ) {
        MovieCardItem(
            movie = it,
            modifier =
            Modifier
                .padding(horizontal = 8.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = true
                )
                .clickable {
                    goToDetails(it)
                }
        )
    }
}
