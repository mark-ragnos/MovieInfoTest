package com.example.movieinfotest.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.example.movieinfotest.utils.isDarkThemeOn
import com.example.movieinfotest.utils.setGone
import com.example.movieinfotest.utils.setVisible
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDarkMode()
        setupNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
