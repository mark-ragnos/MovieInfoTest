package com.example.movieinfotest.presentation.screens.random

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.screens.views.ImageDescriptionMovie
import com.example.movieinfotest.presentation.screens.views.MovieList
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.utils.isCorrectYear
import com.example.movieinfotest.utils.isPossibleYear
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RandomScreen(
    activityViewModel: MainActivityViewModel,
    factory: AppViewModelFactory,
    goToDescription: (MovieDomain) -> Unit
) {
    Log.d("TEST", "RandomScreen Recomposition")
    val randomViewModel: RandomViewModel = viewModel(
        factory = factory
    )
    Log.d("TEST", "RandomVM: $randomViewModel")
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

        Spacer(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
                .background(Color.Black)
        )

        MovieList(
            movies = movies,
            displayItem = {
                ImageDescriptionMovie(movie = it, onItemClick = goToDescription)
            }
        )

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
    Log.d("TEST", "EditTools Recomposition")
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
            YearTextField(year = year, setYear = setYear)
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            GenreSelector(genres = genres, genre = genre, setGenre = setGenre)
        }

        IconButton(
            onClick = { clearFilter() },
            modifier = Modifier.padding(end = 8.dp, start = 8.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_clear
                ),
                contentDescription = "Clear",
                modifier = Modifier.scale(2f)
            )
        }

        IconButton(
            onClick = { generateMovie(year, genre) },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_dice
                ),
                contentDescription = "Generate",
                modifier = Modifier.scale(1.4f)
            )
        }
    }
}

@Composable
private fun YearTextField(
    year: String,
    setYear: (String) -> Unit
) {
    Log.d("TEST", "YearTextField Recomposition")
    val (error, setError) = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = year,
        onValueChange = {
            if (isCorrectYear(it)) {
                setYear(it)
                setError(!isPossibleYear(it))
            }
        },
        label = {
            Text(text = stringResource(id = R.string.random_year_hint))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        isError = error
    )
}

@Composable
private fun GenreSelector(
    genres: List<GenreDomain>,
    genre: GenreDomain,
    setGenre: (GenreDomain) -> Unit
) {
    Log.d("TEST", "GenreSelector Recomposition")
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
