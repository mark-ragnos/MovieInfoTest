package com.example.movieinfotest.ui.popular


import Results
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieinfotest.Repository

class PopularViewModel(private val repository: Repository) : ViewModel() {
    val data: MutableLiveData<List<Results>> by lazy {
        MutableLiveData<List<Results>>()
    }


}