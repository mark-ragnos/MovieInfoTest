package com.example.movieinfotest.ui.details

import MovieDetails
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentMovieInfoBinding
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.utils.getGenreList
import com.example.movieinfotest.utils.getYear
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieInfoBinding
    private lateinit var viewModel: DetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(DetailsViewModel::class.java)
        setupReadLifeData()

        val saved = savedInstanceState?.getInt("ID")?:0
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.sendID(saved!!)
        }

        return binding.root
    }

    private fun setupReadLifeData(){
        val detailObserver = Observer<MovieDetails>{
            setMovie(it)
        }
        viewModel.getDetails().observe(viewLifecycleOwner, detailObserver)
    }

    private fun setMovie(details: MovieDetails){
        binding.infoDate.text = details.release_date.getYear()
        binding.infoDescription.text = details.overview
        binding.infoGenres.text = getGenreList(details.genres)
        binding.infoName.text = details.title
        binding.infoRating.text = details.vote_average.toString()

        Picasso.get()
            .load("https://www.themoviedb.org/t/p/w1280${details.poster_path}")
            .resize(200, 300)
            .centerCrop()
            .into(binding.infoPoster)
    }

}