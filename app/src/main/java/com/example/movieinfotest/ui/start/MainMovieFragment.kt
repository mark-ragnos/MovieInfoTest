package com.example.movieinfotest.ui.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentMainMovieBinding

class MainMovieFragment : Fragment() {
    private lateinit var binding: FragmentMainMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMovieBinding.inflate(inflater, container, false)
        settingNavigation()
        return binding.root
    }



    private fun settingNavigation(){

        //GOTO Favorite
        binding.btnFavorite.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_mainMovieFragment_to_favoriteList)
        }

        //GOTO Popular
        binding.btnPopular.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_mainMovieFragment_to_popularMovieList)
        }

        //GOTO Random
        binding.btnRandom.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_mainMovieFragment_to_generateMovie)
        }
    }

}