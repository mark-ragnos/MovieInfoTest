package com.example.movieinfotest.ui.details

import MovieDetails
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentMovieInfoBinding
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.utils.getGenreList
import com.example.movieinfotest.utils.getYear
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        return binding.root
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