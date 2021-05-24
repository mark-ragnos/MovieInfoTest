package com.example.movieinfotest

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivityViewModel : ViewModel() {
    var isDarkMode: Boolean = false
    var isDarkModeControl: Boolean? = null
    val auth: FirebaseAuth = Firebase.auth

    fun changeDarkMode() {
        if (!isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        isDarkMode = !isDarkMode
    }
}
