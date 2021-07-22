package com.example.movieinfotest.presentation.screens.popular

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.example.movieinfotest.presentation.ui.popular.PopularViewModel

@Composable
fun PopularScreen(
    popularViewModel: PopularViewModel
) {
    Log.d("TEST", "PopularVM: $popularViewModel")
}
