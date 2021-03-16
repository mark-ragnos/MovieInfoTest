package com.example.movieinfotest


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieinfotest.databinding.ActivityMainBinding
import com.example.movieinfotest.repositories.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//64561f5c70d6ee91504935b9f83a94a07455e910

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragment)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setupDarkMode()
        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_menu, menu)

        if (viewModel.getDarkMode())
            menu?.getItem(0)?.icon = getDrawable(R.drawable.ic_light_mode)
        else
            menu?.getItem(0)?.icon = getDrawable(R.drawable.ic_dark_mode)

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


    companion object {

        fun isOnline(context: Context): Boolean {
            if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)) {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (connectivityManager != null) {
                    val capabilities =
                        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return true
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return true
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                            return true
                        }
                    }
                }
                return false
            } else {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                return activeNetwork?.isConnectedOrConnecting == true
            }
        }
    }
}