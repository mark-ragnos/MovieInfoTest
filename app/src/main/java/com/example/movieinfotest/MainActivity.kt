package com.example.movieinfotest


import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragment)
        auth = Firebase.auth

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setupDarkMode()
        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_menu, menu)

        if (viewModel.getDarkMode())
            menu?.getItem(2)?.icon =ContextCompat.getDrawable(applicationContext, R.drawable.ic_light_mode)
        else
            menu?.getItem(2)?.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_dark_mode)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            menu?.getItem(2)?.iconTintList = ColorStateList.valueOf(Color.WHITE)
        }
        if(auth.currentUser != null)
            menu?.getItem(1)?.isVisible = true
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dark_mode_btn -> {
                if (!viewModel.getDarkMode()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                viewModel.changeMode()
            }
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.login -> {
                binding.bottomNavigation.visibility = View.GONE
            }
            R.id.logout -> {
                auth.signOut()
                this.recreate()
            }
        }


        return super.onOptionsItemSelected(item)
    }

    private fun setupDarkMode() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            if (viewModel.getDarkMode() == resources.configuration.isNightModeActive) {
            } else {
                viewModel.changeMode()
            }
        }
    }
}