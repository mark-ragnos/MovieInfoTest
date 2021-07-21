package com.example.movieinfotest.presentation.screens.random

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.presentation.screens.views.DefaultToolbarActions
import com.example.movieinfotest.presentation.screens.views.ToolbarWithoutBack
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.presentation.ui.random.RandomViewModel
import com.example.movieinfotest.utils.isCorrectYear
import com.example.movieinfotest.utils.isPossibleYear

@Composable
fun RandomScreen(
    activityViewModel: MainActivityViewModel,
    viewModel: RandomViewModel
) {
    viewModel.loadGenres()
    val genres by viewModel.genres.collectAsState()
    val year by viewModel.year.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()


    Scaffold(
        topBar = {
            RandomToolbar(viewModel = activityViewModel)
        }
    ) {
        EditTools(
            genres = genres ?: listOf(),
            year = year,
            setYear = { viewModel.setYear(it) },
            genre = selectedGenre,
            setGenre = { viewModel.setGenre(it) },
            generateMovie = { year, genre ->

            },
            clearFilter = {
                viewModel.clearSelectedGenre()
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
            GenreSelector(genres = genres, genre = genre, setGenre = setGenre)
        }

        IconButton(
            onClick = { clearFilter() }
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_clear
                ),
                contentDescription = "Clear"
            )
        }

        IconButton(onClick = { generateMovie(year, genre) }) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_dice
                ),
                contentDescription = "Generate"
            )
        }
    }
}

@Composable
private fun YearTextField(
    year: String,
    setYear: (String) -> Unit
) {
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
        maxLines = 1,
        label = {
            Text(text = stringResource(id = R.string.random_year_hint))
        },
        placeholder = {
            Text(text = "Any year")
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
    val (visibleList, setVisibleList) = remember {
        mutableStateOf(false)
    }

    TextField(
        value = genre.name,
        onValueChange = {},
        readOnly = true,
        label = {
            Text(text = stringResource(id = R.string.random_genre_hint))
        },
        placeholder = {
            Text(text = "Any genre")
        },
        maxLines = 1,
        modifier = Modifier.onFocusChanged {
            setVisibleList(it.isFocused)
        }
    )

    DropdownMenu(
        expanded = visibleList,
        onDismissRequest = { setVisibleList(false) }
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

@Composable
fun RandomToolbar(viewModel: MainActivityViewModel) {
    val login by viewModel.login.collectAsState()
    val darkMode by viewModel.darkMode.collectAsState()

    ToolbarWithoutBack(title = stringResource(id = R.string.random_title)) {
        DefaultToolbarActions(
            darkModeOn = darkMode,
            changeDarkMode = { viewModel.changeDarkMode(it) },
            isLogin = login,
            login = {},
            logout = { viewModel.logout() }
        )
    }
}
