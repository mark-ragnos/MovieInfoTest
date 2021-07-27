package com.example.movieinfotest.presentation.ui.random

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.composite.layouts.LazyColumnWithDecorators
import com.example.movieinfotest.presentation.ui.composite.widgets.SimpleIconButton
import com.example.movieinfotest.presentation.ui.composite.widgets.YearInputTextField
import com.example.movieinfotest.presentation.ui.views.ImageDescriptionMovie

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RandomScreen(
    randomViewModel: RandomViewModel,
    goToDescription: (MovieDomain) -> Unit
) {
    randomViewModel.loadGenres()

    val genres by randomViewModel.genres.collectAsState()
    val year by randomViewModel.year.collectAsState()
    val selectedGenre by randomViewModel.selectedGenre.collectAsState()
    val movies by randomViewModel.movies.collectAsState()

    Column {
        EditTools(
            genres = genres ?: listOf(),
            year = year,
            setYear = { randomViewModel.setYear(it) },
            genre = selectedGenre,
            setGenre = { randomViewModel.setGenre(it) },
            generateMovie = { year, genre ->
                randomViewModel.generateRandom(genre, year)
            },
            clearFilter = {
                randomViewModel.clearSelectedGenre()
            }
        )

        Divider(modifier = Modifier.padding(top = 8.dp))

        LazyColumnWithDecorators(items = movies) {
            ImageDescriptionMovie(movie = it, onItemClick = goToDescription)
        }
    }
}

@Composable
private fun EditTools(
    genres: List<GenreDomain>,
    year: String,
    setYear: (String) -> Unit,
    genre: GenreDomain,
    setGenre: (GenreDomain) -> Unit,
    generateMovie: (String, GenreDomain) -> Unit,
    clearFilter: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomEndPercent = 30,
                    bottomStartPercent = 30
                )
            )
            .padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            YearInputTextField(year = year, setYear = setYear)
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            GenreSelector(genres = genres, genre = genre, setGenre = setGenre)
        }

        SimpleIconButton(
            modifier = Modifier.padding(end = 8.dp, start = 8.dp),
            imageModifier = Modifier.scale(2f),
            onCLick = { clearFilter() },
            painter = painterResource(id = R.drawable.ic_clear),
            contentDescription = "Clear"
        )

        SimpleIconButton(
            modifier = Modifier.padding(end = 8.dp),
            imageModifier = Modifier.scale(1.4f),
            onCLick = { generateMovie(year, genre) },
            painter = painterResource(id = R.drawable.ic_dice),
            contentDescription = "Generate"
        )
    }
}

@Composable
private fun GenreSelector(
    genres: List<GenreDomain>,
    genre: GenreDomain,
    setGenre: (GenreDomain) -> Unit
) {
    val (visibleList, setVisibleList) = remember {
        mutableStateOf(false)
    }
    Column {
        OutlinedTextField(
            value = genre.name,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = stringResource(id = R.string.random_genre_hint))
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dropdown_arrow),
                    contentDescription = "Show",
                    modifier = Modifier
                        .rotate(0f)
                        .clickable {
                            setVisibleList(!visibleList)
                        } // TODO (Add animation)
                )
            }
        )

        DropdownMenu(
            expanded = visibleList,
            onDismissRequest = { setVisibleList(false) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            genres.forEach {
                DropdownMenuItem(onClick = {
                    setGenre(it)
                    setVisibleList(false)
                }) {
                    Text(text = it.name)
                }
            }
        }
    }
}
