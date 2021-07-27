package com.example.movieinfotest.presentation.ui.composite.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.movieinfotest.presentation.ui.composite.pagination.LoadingStatus

@Composable
fun RoundedCenterScreenPlaceholder() = Box(Modifier.fillMaxSize()) {
    LoadingStatus(modifier = Modifier.align(Alignment.Center))
}
