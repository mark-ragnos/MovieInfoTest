package com.example.movieinfotest.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.movieinfotest.MovieApp

object NetworkConnection {
    enum class STATUS {
        ONLINE,
        OFFLINE
    }

    fun isOnline(): Boolean {
        return isOnline(MovieApp.getInstance())
    }

    fun isOnline(context: Context): Boolean {
        return getNetworkStatus(context) == STATUS.ONLINE
    }

    fun getNetworkStatus(context: Context): STATUS {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            newVersion(context)
        } else {
            oldVersion(context)
        }
    }

    private fun oldVersion(context: Context): STATUS {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return if (activeNetwork?.isConnectedOrConnecting == true) STATUS.ONLINE else STATUS.OFFLINE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun newVersion(context: Context): STATUS {
        val connectivityManager: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return STATUS.ONLINE
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return STATUS.ONLINE
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return STATUS.ONLINE
                }
            }
        }
        return STATUS.OFFLINE
    }
}

fun NetworkConnection.STATUS.isOnline(): Boolean {
    return this == NetworkConnection.STATUS.ONLINE
}
