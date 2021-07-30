package com.example.movieinfotest.presentation.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.ui.composite.widgets.EmailInputTextField
import com.example.movieinfotest.presentation.ui.composite.widgets.PasswordInputTextField
import com.example.movieinfotest.utils.FirebaseEvent

@Composable
fun EmailAndPasswordBox(
    modifier: Modifier = Modifier,
    email: String,
    setEmail: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    isCorrectDataInput: (Boolean) -> Unit = {},
) {
    val emailError = remember {
        mutableStateOf(false)
    }
    val passwordError = remember {
        mutableStateOf(false)
    }

    isCorrectDataInput(!emailError.value && !passwordError.value)

    Column(
        modifier = modifier
    ) {
        EmailInputTextField(
            modifier = Modifier.fillMaxWidth(),
            email = email,
            setEmail = setEmail,
            error = emailError.value,
            setError = {
                emailError.value = it
            }
        )

        PasswordInputTextField(
            modifier = Modifier.fillMaxWidth(),
            password = password,
            setPassword = setPassword,
            error = passwordError.value,
            setError = {
                passwordError.value = it
            }
        )
    }
}

@Composable
fun FirebaseStatusDisplay(
    status: FirebaseEvent,
    onSuccessAction: () -> Unit
) {
    when (status) {
        FirebaseEvent.Error -> {
            Text(text = stringResource(id = R.string.autentification_failed))
        }
        FirebaseEvent.Progress -> {
            CircularProgressIndicator()
        }
        FirebaseEvent.Success -> {
            onSuccessAction()
        }
        else -> {
        }
    }
}
