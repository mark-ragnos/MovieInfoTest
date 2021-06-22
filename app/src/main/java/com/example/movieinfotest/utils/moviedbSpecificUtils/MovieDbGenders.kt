package com.example.movieinfotest.utils.moviedbSpecificUtils

import android.content.Context
import com.example.movieinfotest.R

const val NOT_SPECIFIED = 0
const val FEMALE = 1
const val MALE = 2
const val NON_BINARY = 3

fun getGenderText(gender: Int, context: Context): String {
    if (gender !in NOT_SPECIFIED..NON_BINARY) {
        return context.getString(R.string.unknown_gender)
    }
    return gender.toGenderText(context)
}

private fun Int.toGenderText(context: Context): String {
    return when (this) {
        MALE -> context.getString(R.string.male)
        FEMALE -> context.getString(R.string.female)
        NON_BINARY -> context.getString(R.string.non_binary)
        else -> context.getString(R.string.not_specified)
    }
}
