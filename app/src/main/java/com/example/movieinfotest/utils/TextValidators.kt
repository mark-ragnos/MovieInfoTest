package com.example.movieinfotest.utils

fun isCorrectUserData(email: String, password: String): Boolean {
    return (isCorrectEmail(email) && isCorrectPassword(password))
}

fun isCorrectEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isCorrectPassword(password: String): Boolean {
    return password.length >= 8
}
