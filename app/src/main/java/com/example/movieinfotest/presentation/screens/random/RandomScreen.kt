package com.example.movieinfotest.presentation.screens.random

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
//            .background(color = Color.Red)
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
    OutlinedTextField(
        value = year,
        onValueChange = {
            if (it.length <= 4) {
                setYear(it)
            }
        },
        maxLines = 1,
        label = {
            Text(text = stringResource(id = R.string.random_year_hint))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
    )
}

@Composable
private fun GenreSelector(
    genres: List<GenreDomain>,
    genre: GenreDomain,
    setGenre: (GenreDomain) -> Unit
) {

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
