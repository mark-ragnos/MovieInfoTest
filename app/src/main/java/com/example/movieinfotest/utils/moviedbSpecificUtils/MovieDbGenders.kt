package com.example.movieinfotest.utils.moviedbSpecificUtils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.movieinfotest.R

const val NOT_SPECIFIED = 0
const val FEMALE = 1
const val MALE = 2
const val NON_BINARY = 3

@Composable
fun getGenderText(gender: Int): String {
    if (gender !in NOT_SPECIFIED..NON_BINARY) {
        return stringResource(R.string.unknown_gender)
    }

    return gender.toGenderText()
}

@Composable
private fun Int.toGenderText(): String {
    return when (this) {
        MALE -> stringResource(R.string.male)
        FEMALE -> stringResource(R.string.female)
        NON_BINARY -> stringResource(R.string.non_binary)
        else -> stringResource(R.string.not_specified)
    }
}
