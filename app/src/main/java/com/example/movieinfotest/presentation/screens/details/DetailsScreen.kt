package com.example.movieinfotest.presentation.screens.details

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel

@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel,
    movieId: Int
) {
    detailsViewModel.getMovieInfo(movieId)

    val movie by detailsViewModel.movieDetails.collectAsState()
    val isFavorite by detailsViewModel.isFavorite.collectAsState()

    if (movie == null) {
        DetailsScreenPlaceholder()
    } else {
        Text(text = "${movie?.title} in favorite ? ${isFavorite}")
    }
}

@Composable
private fun DetailsScreenPlaceholder() {
    CircularProgressIndicator()
}
