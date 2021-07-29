package com.example.movieinfotest.presentation.ui.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.composite.layouts.RoundedCenterScreenPlaceholder
import com.example.movieinfotest.presentation.ui.views.GenreList
import com.example.movieinfotest.presentation.ui.views.ImageLoader
import com.example.movieinfotest.presentation.ui.composite.widgets.RatingBarWithTextPercents
import com.example.movieinfotest.utils.FLAG_BACKDROP_W780
import com.example.movieinfotest.utils.POSTER_IMAGE_SIZE_DETAILS
import com.example.movieinfotest.utils.RATING_MULT
import com.example.movieinfotest.utils.RATIO_BACKDROP

@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel = viewModel(),
    movieId: Int = 0,
    moveToActor: (Int) -> Unit = {}
) {
    detailsViewModel.getMovieInfo(movieId)

    val movie by detailsViewModel.movieDetails.collectAsState()
    val isFavorite by detailsViewModel.isFavorite.collectAsState()

    if (movie == null) {
        RoundedCenterScreenPlaceholder()
    } else {
        MovieDetailsContent(
            movie = movie!!,
            isFavorite = isFavorite,
            favoriteClick = {
                if (isFavorite) {
                    detailsViewModel.deleteFromFavorite()
                } else {
                    detailsViewModel.saveInFavorite()
                }
            },
            actorClick = moveToActor
        )
    }
}

@Composable
private fun MovieDetailsContent(
    movie: MovieDomain,
    favoriteVisible: Boolean = true,
    isFavorite: Boolean = false,
    favoriteClick: () -> Unit = {},
    actorClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        BackdropPosterPlace(
            modifier = Modifier.fillMaxWidth(),
            backdropPath = movie.backdropPath,
            posterPath = movie.posterPath,
            favoriteVisible = favoriteVisible,
            isFavorite = isFavorite,
            favoriteClick = favoriteClick
        )

        MovieDescription(
            movie.title,
            movie.overview,
            movie.releaseDate,
            movie.voteAverage,
            movie.genres
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        ActorContent(
            cast = movie.casts,
            crew = movie.crews,
            onItemClick = actorClick
        )

        Spacer(modifier = Modifier.padding(bottom = 4.dp))
    }
}

@Composable
private fun BackdropPosterPlace(
    modifier: Modifier = Modifier,
    backdropPath: String?,
    posterPath: String?,
    favoriteVisible: Boolean = true,
    isFavorite: Boolean = false,
    favoriteClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        ImageLoader(
            path = backdropPath, imageResolution = FLAG_BACKDROP_W780,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(RATIO_BACKDROP)
        )

        ImageLoader(
            path = posterPath,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(POSTER_IMAGE_SIZE_DETAILS.width, POSTER_IMAGE_SIZE_DETAILS.height)
                .align(Alignment.CenterStart)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp), true)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        if (favoriteVisible) {
            val icon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_not
            IconButton(
                onClick = favoriteClick,
                modifier = Modifier
                    .padding(
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
private fun MovieDescription(
    title: String,
    overview: String,
    releaseDate: String?,
    voteAverage: Double,
    genres: List<GenreDomain>?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "$title ($releaseDate)",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )

        Divider(Modifier.padding(vertical = 8.dp))

        RatingAndGenres(
            Modifier.padding(vertical = 4.dp),
            voteAverage,
            genres
        )

        Divider(Modifier.padding(vertical = 8.dp))

        Text(
            text = overview,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun RatingAndGenres(
    modifier: Modifier = Modifier,
    voteAverage: Double,
    genres: List<GenreDomain>?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RatingBarWithTextPercents(value = (voteAverage * RATING_MULT).toFloat())

        Text(
            text = stringResource(id = R.string.details_user_rating),
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        if (genres != null) {
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            GenreList(genres = genres)
        }
    }
}
