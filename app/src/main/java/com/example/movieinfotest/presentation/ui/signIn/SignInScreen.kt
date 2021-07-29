package com.example.movieinfotest.presentation.ui.signIn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.ui.composite.widgets.EmailInputTextField
import com.example.movieinfotest.presentation.ui.composite.widgets.PasswordInputTextField
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.presentation.ui.views.EmailAndPasswordBox
import com.example.movieinfotest.presentation.ui.views.FirebaseStatusDisplay
import com.example.movieinfotest.utils.FirebaseEvent
import com.example.movieinfotest.utils.isCorrectEmail
import com.example.movieinfotest.utils.isCorrectPassword

@Composable
fun SignInScreen(
    mainActivityViewModel: MainActivityViewModel = viewModel()
) {
    val (email, setEmail) = remember {
        mutableStateOf("")
    }
    val (password, setPassword) = remember {
        mutableStateOf("")
    }
    val accessToLogin = remember {
        mutableStateOf(false)
    }
    val status by mainActivityViewModel.firebaseEventBus.collectAsState(initial = FirebaseEvent.Nothing)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .align(Alignment.Center)
        ) {
            EmailAndPasswordBox(
                modifier = Modifier.fillMaxWidth(),
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
                    mainActivityViewModel.register(email, password)
                },
                enabled = accessToLogin.value,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.signin_register),
                    style = MaterialTheme.typography.button
                )
            }
            FirebaseStatusDisplay(status = status)
        }
    }
}
