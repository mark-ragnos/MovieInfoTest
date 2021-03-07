package com.example.movieinfotest.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.R
import com.example.movieinfotest.Repository
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.listadapter.MovieAdapter


class PopularMovieListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: PopularViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)



        return binding.root
    }

    private fun setupUI(){
        //Adapter settings
        binding.rvPopularList.layoutManager = LinearLayoutManager(context)
        binding.rvPopularList.adapter = MovieAdapter()
    }

    private fun addData(){

    }
}