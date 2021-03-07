package com.example.movieinfotest.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding


class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)



        return binding.root
    }

}