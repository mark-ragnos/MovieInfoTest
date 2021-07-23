package com.example.movieinfotest.presentation.screens.views

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.screens.utils.ImageSize
import com.example.movieinfotest.utils.FLAG_W154
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
    }, modifier = Modifier) {
        Icon(
            painter = painterResource(id = favoriteButton),
            contentDescription = "Favorite"
        )
    }
}

@Composable
fun ImageLoader(
    path: String?,
    imageSize: ImageSize,
    imageUri: String = "https://www.themoviedb.org/t/p/$FLAG_W154",
    @DrawableRes placeholder: Int = R.drawable.ic_placeholder_movie
) {

    Image(
        painter = rememberImagePainter(data = imageUri + path) {
            placeholder(placeholder)
            error(placeholder)
        },
        contentDescription = "Poster",
        modifier = Modifier
            .height(imageSize.height)
            .width(imageSize.width)
    )
}
