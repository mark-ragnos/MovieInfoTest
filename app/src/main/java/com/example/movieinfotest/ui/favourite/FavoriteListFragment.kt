package com.example.movieinfotest.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.ui.AppViewModelFactory


class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(FavoriteViewModel::class.java)

        return binding.root
    }

}