package com.example.movieinfotest.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.example.movieinfotest.utils.isDarkThemeOn
import com.example.movieinfotest.utils.setGone
import com.example.movieinfotest.utils.setVisible
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
        setupNavigation()
    }

    private fun setupDarkMode() {
        if (viewModel.isDarkModeControl == null) {
            viewModel.isDarkModeControl = true
            viewModel.isDarkMode = applicationContext.isDarkThemeOn()
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
        registerDestinationListener(navHostFragment.navController)
    }

    private fun registerDestinationListener(navController: NavController) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.registrationFragment -> {
                    binding.bottomNavigation.setGone()
                }
                R.id.loginFragment -> {
                    binding.bottomNavigation.setGone()
                }
                else -> {
                    binding.bottomNavigation.setVisible()
                }
            }
        }
    }
}
