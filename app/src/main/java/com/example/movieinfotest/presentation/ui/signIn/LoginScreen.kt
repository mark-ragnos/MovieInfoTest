package com.example.movieinfotest.presentation.ui.signIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.presentation.ui.views.EmailAndPasswordBox
import com.example.movieinfotest.presentation.ui.views.FirebaseStatusDisplay
import com.example.movieinfotest.utils.FirebaseEvent
import com.example.movieinfotest.utils.isCorrectEmail
import com.example.movieinfotest.utils.isCorrectPassword

@Composable
fun LoginScreen(
    activityViewModel: MainActivityViewModel = viewModel(),
    goToRegistration: () -> Unit = {},
    goBack: () -> Unit = {}
) {
    if (activityViewModel.login.value) {
        goBack()
    }

    val (email, setEmail) = rememberSaveable {
        mutableStateOf("")
    }
    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }
    val accessToLogin = rememberSaveable {
        mutableStateOf(false)
    }
    val status by activityViewModel.firebaseEventBus.collectAsState(initial = FirebaseEvent.Nothing)

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
                isCorrectDataInput = {
                    accessToLogin.value = it && isCorrectPassword(password) && isCorrectEmail(email)
                }
            )
            Button(
                onClick = {
                    activityViewModel.login(email, password)
                },
                enabled = accessToLogin.value,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.login_login),
                    style = MaterialTheme.typography.button
                )
            }
            Text(
                text = stringResource(id = R.string.login_help),
                modifier = Modifier.clickable {
                    goToRegistration()
                }
            )

            FirebaseStatusDisplay(
                status = status,
                onSuccessAction = goBack
            )
        }
    }
}
