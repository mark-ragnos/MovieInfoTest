package com.example.movieinfotest.presentation.screens.details

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.movieinfotest.domain.entities.actor.CastDomain
import com.example.movieinfotest.domain.entities.actor.CrewDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.screens.views.LoadingStatus
import com.example.movieinfotest.presentation.ui.details.DetailsViewModel

@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel,
    movieId: Int,
    moveToActor: (Int) -> Unit
) {
    detailsViewModel.getMovieInfo(movieId)

    val movie by detailsViewModel.movieDetails.collectAsState()
    val isFavorite by detailsViewModel.isFavorite.collectAsState()

    if (movie == null) {
        DetailsScreenPlaceholder()
    } else {
        MovieDetailsContent(
            movie = movie!!,
            isFavorite = isFavorite,
            favoriteClick = {
                if (isFavorite) {
                    detailsViewModel.saveInFavorite()
                } else {
                    detailsViewModel.deleteFromFavorite()
                }
            },
            actorClick = moveToActor
        )
    }
}

@Composable
fun MovieDetailsContent(
    movie: MovieDomain,
    isFavorite: Boolean,
    favoriteClick: () -> Unit,
    actorClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = movie.title)

        ActorContent(cast = movie.casts, crew = movie.crews, onItemClick = actorClick)
    }
}

const val CAST = "Cast"
const val CREW = "Crew"

@Composable
fun ActorContent(
    cast: List<CastDomain>?,
    crew: List<CrewDomain>?,
    onItemClick: (Int) -> Unit = {}
) {
    val (visibleElement, setVisibleElement) = remember {
        mutableStateOf(
            when {
                !cast.isNullOrEmpty() -> CAST
                !crew.isNullOrEmpty() -> CREW
                else -> null
            }
        )
    }

    if (visibleElement == CAST) {
        ActorList(actors = cast!!, displayItem = {
            ActorCard(
                id = it.id,
                name = it.name,
                role = it.character,
                profilePath = it.profilePath,
                gender = it.gender,
                onCardClick = onItemClick
            )
        })
    }
    if (visibleElement == CREW) {
        ActorList(actors = crew!!, displayItem = {
            ActorCard(
                id = it.id,
                name = it.name,
                role = it.job,
                profilePath = it.profilePath,
                gender = it.gender
            )
        })
    }
}

@Composable
private fun DetailsScreenPlaceholder() {
    Box(Modifier.fillMaxSize()) {
        LoadingStatus(modifier = Modifier.align(Alignment.Center))
    }
}
