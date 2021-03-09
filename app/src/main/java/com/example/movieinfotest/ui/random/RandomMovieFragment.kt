package com.example.movieinfotest.ui.random

import Results
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentGenerateMovieBinding
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

        setupUI()
        setupReadLifeData()


        return binding.root
    }

    private fun setupUI() {
        binding.genBtnRandom.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.generateRandom("12", "2000")
            }
        }
    }

    private fun setupReadLifeData() {
        val detailObserver = Observer<Results> {
            setMovie(it)
        }
        viewModel.getRandom().observe(viewLifecycleOwner, detailObserver)
    }

    private fun setMovie(movie: Results) {
        binding.genOutPoster.registerImage(movie.poster_path, x = 150, y = 225)
        binding.genOutRating.text = movie.vote_average.toString()
        binding.genOutName.text = movie.title
    }

}