package com.example.movieinfotest.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentGenerateMovieBinding


class RandomMovieFragment : Fragment() {
    private lateinit var binding: FragmentGenerateMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenerateMovieBinding.inflate(inflater, container, false)



        return binding.root
    }

}