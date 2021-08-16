package com.example.movieinfotest.presentation.ui.popular

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.composite.pagination.PagingLazyColumn
import com.example.movieinfotest.presentation.ui.composite.widgets.ImageLoader
import com.example.movieinfotest.presentation.ui.composite.widgets.SimpleIconButton
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.utils.POSTER_IMAGE_SIZE

@Composable
fun PopularScreen(
    popularViewModel: PopularViewModel = viewModel(),
    mainViewModel: MainActivityViewModel = viewModel(),
    goToDescription: (MovieDomain) -> Unit = {}
) {
    val isLogin = mainViewModel.login.collectAsState()
    val movies = popularViewModel.movies.collectAsLazyPagingItems()
    val favorites = popularViewModel.favoriteMoviesIdsFlow.collectAsState(initial = listOf())

    PagingLazyColumn(items = movies) {
        PopularMovieCardItem(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = true
                )
                .clickable { goToDescription(it) },
            movie = it,
            isVisibleFavoriteBtn = isLogin.value,
            isFavorite = favorites.value.contains(it.id),
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

@Composable
fun PopularMovieCardItem(
    modifier: Modifier = Modifier,
    movie: MovieDomain,
    isVisibleFavoriteBtn: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteClick: (MovieDomain, Boolean) -> Unit = { _, _ -> }
) {
    Card(
        modifier = modifier
    ) {
        Row {
            ImageLoader(
                path = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .size(POSTER_IMAGE_SIZE.width, POSTER_IMAGE_SIZE.height)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f)
            ) {
                Text(text = movie.title, maxLines = 2)
                Text(text = movie.voteAverage.toString())
            }

            if (isVisibleFavoriteBtn) {
                FavoriteButton(
                    movie = movie,
                    isFavorite = isFavorite,
                    onFavoriteClick = { movie: MovieDomain, isFavorite: Boolean ->
                        onFavoriteClick(movie, isFavorite)
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    movie: MovieDomain,
    isFavorite: Boolean = false,
    onFavoriteClick: (MovieDomain, Boolean) -> Unit = { _, _ -> }
) {
    val favoriteButton =
        if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_not

    SimpleIconButton(
        modifier = modifier,
        onCLick = { onFavoriteClick(movie, isFavorite) },
        painter = painterResource(id = favoriteButton),
        contentDescription = stringResource(id = R.string.cd_details_add_in_favorite)
    )
}
