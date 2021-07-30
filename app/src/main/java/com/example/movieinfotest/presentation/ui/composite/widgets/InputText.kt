package com.example.movieinfotest.presentation.ui.composite.widgets

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.movieinfotest.R
import com.example.movieinfotest.utils.isCorrectEmail
import com.example.movieinfotest.utils.isCorrectEmailInput
import com.example.movieinfotest.utils.isCorrectPassword
import com.example.movieinfotest.utils.isCorrectPasswordInput
import com.example.movieinfotest.utils.isCorrectYearInput
import com.example.movieinfotest.utils.isCorrectYear

@Composable
fun YearInputTextField(
    modifier: Modifier = Modifier,
    year: String,
    setYear: (String) -> Unit,
    error: Boolean,
    setError: (Boolean) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = year,
        onValueChange = {
            if (isCorrectYearInput(it)) {
                setYear(it)
                setError(!isCorrectYear(it))
            }
        },
        label = {
            Text(text = stringResource(id = R.string.random_year_hint))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        isError = error,
        singleLine = true
    )
}

@Composable
fun PasswordInputTextField(
    modifier: Modifier = Modifier,
    password: String,
    setPassword: (String) -> Unit,
    error: Boolean,
    setError: (Boolean) -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val (visiblePassword, setVisiblePassword) = rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = {
            if (isCorrectPasswordInput(it)) {
                setPassword(it)
                setError(!isCorrectPassword(it))
            }
        },
        label = {
            Text(text = stringResource(id = R.string.password))
        },
        trailingIcon = {
            val icon = when {
                visiblePassword -> R.drawable.ic_visibility
                else -> R.drawable.ic_visibility_off
            }

            SimpleIconButton(
                onCLick = {
                    setVisiblePassword(!visiblePassword)
                },
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.cd_change_password_visibility)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        isError = error,
        visualTransformation = if (!visiblePassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true
    )
}

@Composable
fun EmailInputTextField(
    modifier: Modifier = Modifier,
    email: String,
    setEmail: (String) -> Unit,
    error: Boolean,
    setError: (Boolean) -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = email,
        onValueChange = {
            if (isCorrectEmailInput(it)) {
                setEmail(it)
                setError(!isCorrectEmail(it))
            }
        },
        label = {
            Text(text = stringResource(id = R.string.email))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        isError = error,
        singleLine = true
    )
}
