package com.example.movieinfotest.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentMovieInfoBinding
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.ui.details.actors.ActorAdapter
import com.example.movieinfotest.utils.getGenreList
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.registerImage
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

        val saved = DetailsFragmentArgs.fromBundle(requireArguments()).id
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.sendID(saved)
        }
        (activity as MainActivity).supportActionBar?.title = (activity as MainActivity).resources.getString(
            R.string.details_title)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        return binding.root
    }

    private fun setupReadLifeData() {
        val detailObserver = Observer<MovieDetails> {
            setMovie(it)
        }
        viewModel.getDetails().observe(viewLifecycleOwner, detailObserver)

        val actorObserver = Observer<List<Actor>>{
            setActors(it)
        }
        viewModel.getActors().observe(viewLifecycleOwner, actorObserver)
    }

    private fun setMovie(details: MovieDetails) {
        binding.infoDate.text = details.release_date.getYear()
        binding.infoDescription.text = details.overview
        binding.infoGenres.text = getGenreList(details.genres)
        binding.infoName.text = details.title
        binding.infoRating.text = details.vote_average.toString()
        binding.infoPoster.registerImage(details.poster_path, x = 150, y = 225)
    }

    private fun setActors(list: List<Actor>){
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        binding.lvActors.layoutManager = manager
        binding.lvActors.adapter = ActorAdapter(list)
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}