package com.example.movieinfotest.presentation.ui.main

import androidx.appcompat.app.AppCompatDelegate
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
//            _login.emit(auth.currentUser != null)
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
        if (!currentDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        viewModelScope.launch {
            _darkMode.emit(!currentDarkMode)
        }
    }
}
