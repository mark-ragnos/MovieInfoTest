package com.example.movieinfotest.presentation.screens.popular

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel

@Composable
fun PopularScreen(
    popularViewModel: PopularViewModel
) {
    Log.d("TEST", "PopularVM: $popularViewModel")
}