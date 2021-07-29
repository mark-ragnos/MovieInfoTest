package com.example.movieinfotest.presentation.ui.composite.widgets

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.movieinfotest.utils.isCorrectYear
import com.example.movieinfotest.utils.isPossibleYear

@Composable
fun YearInputTextField(
    modifier: Modifier = Modifier,
    year: String,
    setYear: (String) -> Unit
) {
    val (error, setError) = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
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
    setError: (Boolean) -> Unit
) {
    val (visiblePassword, setVisiblePassword) = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = {
            if (isCorrectPasswordInput(it)) {
                setPassword(it)
                setError(!isCorrectPassword(password))
            }
        },
        label = {
            Text(text = stringResource(id = R.string.signin_password))
        },
        trailingIcon = {
            val icon =
                if (visiblePassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off

            SimpleIconButton(
                onCLick = {
                    setVisiblePassword(!visiblePassword)
                },
                painter = painterResource(id = icon),
                contentDescription = "Visible password"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
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
    setError: (Boolean) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = email,
        onValueChange = {
            if (isCorrectEmailInput(it)) {
                setEmail(it)
                setError(!isCorrectEmail(email))
            }
        },
        label = {
            Text(text = stringResource(id = R.string.signin_email))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(),
        isError = error,
        singleLine = true
    )
}
