package com.example.movieinfotest.presentation.screens.views

import android.annotation.SuppressLint
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavoriteButton(
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
    }) {
        Icon(
            painter = painterResource(id = favoriteButton),
            contentDescription = "Favorite"
        )
    }
}
