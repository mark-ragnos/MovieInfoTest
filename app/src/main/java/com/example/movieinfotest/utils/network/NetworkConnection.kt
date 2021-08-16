package com.example.movieinfotest.utils.network

object NetworkConnection {
    enum class STATUS {
        ONLINE,
        OFFLINE
    }
}

fun NetworkConnection.STATUS.isOnline(): Boolean {
    return this == NetworkConnection.STATUS.ONLINE
}
