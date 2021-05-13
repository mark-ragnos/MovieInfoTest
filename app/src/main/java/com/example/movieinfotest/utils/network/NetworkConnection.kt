package com.example.movieinfotest.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.example.movieinfotest.MovieApp

class NetworkConnection {
    companion object{
        fun isOnline(): NetworkStatus {
            return isOnline(MovieApp.getInstance())
        }

        fun isOnline(context: Context): NetworkStatus {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val connectivityManager: ConnectivityManager? =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
                if (connectivityManager != null) {
                    val capabilities =
                        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return NetworkStatus.ONLINE
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return NetworkStatus.ONLINE
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                            return NetworkStatus.ONLINE
                        }
                    }
                }
                return NetworkStatus.OFFLINE
            } else {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                return if(activeNetwork?.isConnectedOrConnecting == true) NetworkStatus.ONLINE else NetworkStatus.OFFLINE
            }
        }
    }
}