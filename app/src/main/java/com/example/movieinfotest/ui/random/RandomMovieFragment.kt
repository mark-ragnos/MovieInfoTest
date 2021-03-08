package com.example.movieinfotest.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentGenerateMovieBinding
import com.example.movieinfotest.ui.AppViewModelFactory


class RandomMovieFragment : Fragment() {
    private lateinit var binding: FragmentGenerateMovieBinding
    private lateinit var viewModel: RandomViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenerateMovieBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(RandomViewModel::class.java)

        return binding.root
    }

}