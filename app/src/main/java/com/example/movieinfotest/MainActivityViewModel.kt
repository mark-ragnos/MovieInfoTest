package com.example.movieinfotest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel:ViewModel() {
    private var isDarkMode: Boolean = false

    fun changeMode(){
        isDarkMode = !isDarkMode
    }

    fun getDarkMode():Boolean{
        return isDarkMode
    }
}