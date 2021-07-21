package com.example.movieinfotest.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.screens.main.MainScreen
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private var viewModelFactory: AppViewModelFactory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        viewModelFactory = AppViewModelFactory.getFactory(baseContext)

        setContent {
            MaterialTheme() {
                MainScreen(
                    activityViewModel = viewModel,
                    factory = viewModelFactory
                )

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelFactory = null
    }
}
