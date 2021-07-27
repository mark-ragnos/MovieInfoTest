package com.example.movieinfotest.presentation.ui.composite.widgets

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.movieinfotest.R
import com.example.movieinfotest.utils.isCorrectYear
import com.example.movieinfotest.utils.isPossibleYear

@Composable
fun YearInputTextField(
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
        label = {
            Text(text = stringResource(id = R.string.random_year_hint))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        isError = error
    )
}
