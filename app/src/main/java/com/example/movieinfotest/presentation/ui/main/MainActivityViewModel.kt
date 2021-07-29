package com.example.movieinfotest.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfotest.utils.FirebaseEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _firebaseEventBus = MutableSharedFlow<FirebaseEvent>(extraBufferCapacity = 0)
    val firebaseEventBus = _firebaseEventBus.asSharedFlow()

    fun logout() {
        auth.signOut()
    }

    fun register(email: String, password: String) = viewModelScope.launch {
        _firebaseEventBus.emit(FirebaseEvent.Progress)
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    _firebaseEventBus.emit(FirebaseEvent.Success)
                } else {
                    _firebaseEventBus.emit(FirebaseEvent.Error)
                }
            }
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _firebaseEventBus.emit(FirebaseEvent.Progress)
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    _firebaseEventBus.emit(FirebaseEvent.Success)
                } else {
                    _firebaseEventBus.emit(FirebaseEvent.Error)
                }
            }
        }
    }

    fun changeDarkMode(currentDarkMode: Boolean) {
        viewModelScope.launch {
            _darkMode.emit(!currentDarkMode)
        }
    }
}
