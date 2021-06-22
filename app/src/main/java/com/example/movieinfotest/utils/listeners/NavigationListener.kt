package com.example.movieinfotest.utils.listeners

interface NavigationListener<T> {
    fun navigate(param: T)
}