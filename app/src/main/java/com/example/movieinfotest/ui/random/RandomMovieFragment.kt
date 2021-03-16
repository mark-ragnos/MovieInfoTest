package com.example.movieinfotest.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentGenerateMovieBinding
import com.example.movieinfotest.models.genre.Genre
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


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

    private var accessToMove = false

    private fun setupUI() {
        (activity as MainActivity).supportActionBar?.title = (activity as MainActivity).resources.getString(
            R.string.random_title)
        binding.genBtnRandom.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                binding.genResult.visibility = View.INVISIBLE
                accessToMove = false
                if (startRandom()) {
                    viewModel.generateRandom((binding.genInGenre.selectedItem as Genre).id.toString(), binding.genInYear.text.toString())
                    accessToMove = true
                } else Toast.makeText(MovieApp.getInstance(), "Incorrect format of year", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.genResult.setOnClickListener {
            if (accessToMove) {
                val action = RandomMovieFragmentDirections.actionGenerateMovieToMovieInfo(
                    binding.genOutId.text.toString().toInt()
                )
                NavHostFragment.findNavController(this).navigate(action)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            if(viewModel.getGenres()!=null)
                binding.genInGenre.adapter = GenreAdapter(MovieApp.getInstance(), viewModel.getGenres()!!)
            else{
                Toast.makeText(MovieApp.getInstance(), "Your database don't have any objects. Please set ON Internet and reopen this view", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupReadLifeData() {
        val detailObserver = Observer<Movie> {
            setMovie(it)
        }
        viewModel.getRandom().observe(viewLifecycleOwner, detailObserver)
    }

    private fun setMovie(movie: Movie) {
        binding.genOutPoster.registerImage(movie.poster_path, x = 150, y = 225)
        binding.genOutRating.text = movie.vote_average.toString()
        binding.genOutName.text = movie.title
        binding.genOutId.text = movie.id.toString()
        binding.genResult.visibility = View.VISIBLE
        accessToMove = true
    }

    private suspend fun startRandom(): Boolean {
        if(!MainActivity.isOnline(MovieApp.getInstance()))
            return false

        val today = Calendar.getInstance().get(Calendar.YEAR)
        val year = binding.genInYear.text.toString()

        if (year == "")
            return true

        if (year.toInt() in 1895..today) {
            viewModel.generateRandom(
                (binding.genInGenre.selectedItem as Genre).id.toString(),
                binding.genInYear.text.toString()
            )
        } else return false

        return true
    }
}