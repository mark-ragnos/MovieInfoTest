package com.example.movieinfotest.utils

import java.util.Calendar

fun isCorrectEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isCorrectEmailInput(email: String): Boolean = email.length <= 256

fun isCorrectPassword(password: String): Boolean {
    return password.length in 8..32
}

fun isCorrectPasswordInput(password: String) = password.length <= 32

/**
 * Check input string correct length
 * @param year String param of year
 * @return Boolean, what = length <= 4
 */
fun isCorrectYearInput(year: String): Boolean {
    return (year.length <= 4)
}

fun isCorrectYear(inputYear: String): Boolean {
    if (inputYear.isEmpty()) {
        return true
    }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val realYear = inputYear.toIntOrNull() ?: return false

    if (realYear !in 1895..currentYear) {
        return false
    }

    return true
}
