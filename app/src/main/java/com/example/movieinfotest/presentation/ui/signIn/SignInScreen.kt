package com.example.movieinfotest.presentation.ui.signIn

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.ui.composite.widgets.EmailInputTextField
import com.example.movieinfotest.presentation.ui.composite.widgets.PasswordInputTextField
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.utils.isCorrectEmail
import com.example.movieinfotest.utils.isCorrectPassword

@Composable
fun SignInScreen(
    mainActivityViewModel: MainActivityViewModel = viewModel()
) {
    val isLogin by mainActivityViewModel.login.collectAsState()
    val (email, setEmail) = remember {
        mutableStateOf("")
    }
    val (password, setPassword) = remember {
        mutableStateOf("")
    }
    val accessToLogin = remember {
        mutableStateOf(false)
    }

    Column {
        EmailAndPasswordBox(
            email = email,
            setEmail = setEmail,
            password = password,
            setPassword = setPassword,
            setCorrectEmailAndPassword = {
                accessToLogin.value = it && isCorrectPassword(password) && isCorrectEmail(email)
            }
        )
        Button(
            onClick = {
                Log.d("TEST", "Click")
            },
            enabled = accessToLogin.value
        ) {
            Text(text = stringResource(id = R.string.signin_register))
        }
    }
}

@Composable
fun EmailAndPasswordBox(
    modifier: Modifier = Modifier,
    email: String,
    setEmail: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    setCorrectEmailAndPassword: (Boolean) -> Unit = {},
) {
    val emailError = remember {
        mutableStateOf(false)
    }
    val passwordError = remember {
        mutableStateOf(false)
    }

    setCorrectEmailAndPassword(!emailError.value && !passwordError.value)

    Column(
        modifier = modifier
    ) {
        EmailInputTextField(
            email = email,
            setEmail = setEmail,
            error = emailError.value,
            setError = {
                emailError.value = it
            }
        )

        PasswordInputTextField(
            password =
            password, setPassword = setPassword,
            error = passwordError.value,
            setError = {
                passwordError.value = it
            }
        )
    }
}
