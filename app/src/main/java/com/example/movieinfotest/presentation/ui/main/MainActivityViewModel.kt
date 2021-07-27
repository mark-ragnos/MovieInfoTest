package com.example.movieinfotest.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val auth = Firebase.auth

    init {
        viewModelScope.launch {
        }
    }

    private val _darkMode = MutableStateFlow(false)
    val darkMode = _darkMode.asStateFlow()

    private val _login = MutableStateFlow(false)
    val login = _login.asStateFlow()

    fun logout() {
        auth.signOut()
    }

    fun changeDarkMode(currentDarkMode: Boolean) {
        viewModelScope.launch {
            _darkMode.emit(!currentDarkMode)
        }
    }
}
