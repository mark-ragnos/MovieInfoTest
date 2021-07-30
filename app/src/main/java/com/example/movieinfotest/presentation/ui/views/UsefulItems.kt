package com.example.movieinfotest.presentation.ui.views

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.FLAG_POSTER_W154
import com.example.movieinfotest.utils.ImageSize
import com.example.movieinfotest.utils.POSTER_IMAGE_SIZE
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    movie: MovieDomain,
    isFavorite: suspend (MovieDomain) -> Boolean = { false },
    onFavoriteClick: (MovieDomain, Boolean) -> Unit = { _, _ -> }
) {
    val (favorite, setFavorite) = remember {
        mutableStateOf(false)
    }
    val composeScope = rememberCoroutineScope()
    composeScope.launch {
        setFavorite(isFavorite(movie))
    }

    val favoriteButton =
        if (favorite) R.drawable.ic_favorite else R.drawable.ic_favorite_not

    IconButton(onClick = {
        onFavoriteClick(movie, favorite)
    }, modifier = modifier) {
        Icon(
            painter = painterResource(id = favoriteButton),
            contentDescription = stringResource(id = R.string.cd_details_add_in_favorite)
        )
    }
}
