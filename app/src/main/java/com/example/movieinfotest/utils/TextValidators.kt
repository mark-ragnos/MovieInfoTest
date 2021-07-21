package com.example.movieinfotest.utils

import java.util.Calendar

fun isCorrectUserData(email: String, password: String): Boolean {
    return (isCorrectEmail(email) && isCorrectPassword(password))
}

fun isCorrectEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isCorrectPassword(password: String): Boolean {
    return password.length >= 8
}

/**
 * Check input string correct length
 * @param year String param of year
 * @return Boolean, what = length <= 4
 */
fun isCorrectYear(year: String): Boolean {
    return (year.length <= 4)
}

fun isPossibleYear(inputYear: String): Boolean {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    if (inputYear == "") {
        return true
    }

    if (inputYear.toInt() !in 1895..currentYear) {
        return false
    }

    return true
}
