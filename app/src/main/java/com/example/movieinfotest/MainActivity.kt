package com.example.movieinfotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.example.movieinfotest.utils.isDarkThemeOn
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setupDarkMode()

        val navController = findNavController(R.id.fragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupDarkMode() {
        if (viewModel.isDarkModeControl == null) {
            viewModel.isDarkModeControl = true
            viewModel.isDarkMode = applicationContext.isDarkThemeOn()
        }
    }
}