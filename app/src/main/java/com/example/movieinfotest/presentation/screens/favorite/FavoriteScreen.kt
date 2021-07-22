package com.example.movieinfotest.presentation.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.screens.views.DefaultToolbarActions
import com.example.movieinfotest.presentation.screens.views.LazyMovieList
import com.example.movieinfotest.presentation.screens.views.Toolbar
import com.example.movieinfotest.presentation.ui.favourite.FavoriteViewModel
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.utils.getYear

@Composable
fun FavoriteScreen(
    mainViewModel: MainActivityViewModel,
    viewModel: FavoriteViewModel,
    moveToDetails: (MovieDomain) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    LazyMovieList(
        movies = movies,
        displayItem = {
            MovieItemFavorite(
                movieDomain = it,
                onItemClick = {
                    moveToDetails(it)
                }
            )
        }
    )

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
        Column {
            Text(
                text = "${movieDomain.title} (${movieDomain.releaseDate.getYear()})"
            )
            Text(
                text = "Rating: ${movieDomain.voteAverage}"
            )
        }
    }
}


@Preview
@Composable
fun PreviewFavorite() {

}
