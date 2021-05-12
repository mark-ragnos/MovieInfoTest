package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentGenerateMovieBinding
import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.utils.network.NetworkStatus
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToastUtils
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
    ): View {
        binding = FragmentGenerateMovieBinding.inflate(inflater, container, false)

        init()
        initGenreList()
        setupUI()
        setupReadLifeData()

        return binding.root
    }

    private fun init() {
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(RandomViewModel::class.java)

        (activity as MainActivity).supportActionBar?.title =
            (activity as MainActivity).resources.getString(
                R.string.random_title
            )
    }


    private fun setupUI() {
        binding.genBtnRandom.setOnClickListener {
            lifecycle.coroutineScope.launch(Dispatchers.Main) {
                if (isGenerateAccess()) {
                    onProgress(true)
                    viewModel.generateRandom(
                        (binding.genInGenre.selectedItem as Genre).id.toString(),
                        binding.genInYear.text.toString()
                    )
                }
            }
        }

        binding.genResult.setOnClickListener {
            val action = RandomMovieFragmentDirections.actionGenerateMovieToMovieInfo(
                binding.genOutId.text.toString().toInt()
            )
            NavHostFragment.findNavController(this).navigate(action)

        }

    }

    private fun initGenreList() {
        lifecycle.coroutineScope.launch(Dispatchers.Main) {
            if (viewModel.getGenres() != null) {
                val adapter = context?.let { GenreAdapter(it, viewModel.getGenres()!!) }
                binding.genInGenre.adapter = adapter
                buttonEnabled(true)
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
        binding.genOutDesc.text = movie.overview
        onProgress(false)
    }

    private fun isGenerateAccess(): Boolean {
        if (NetworkConnection.isOnline(MovieApp.getInstance()) == NetworkStatus.OFFLINE) {
            makeMessage(resources.getText(R.string.internet_not_found))
            return false
        }
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val inputYear = binding.genInYear.text.toString()

        if (inputYear == "")
            return true

        if (inputYear.toInt() !in 1895..currentYear) {
            makeMessage(resources.getText(R.string.year_incorrect))
            return false
        }

        return true
    }

    private fun makeMessage(message: CharSequence) {
        context?.let { ToastUtils.makeShortMessage(it, message.toString()) }
    }

    private fun onProgress(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.GONE
        binding.genResult.visibility = if (!isProgress) View.VISIBLE else View.INVISIBLE
        buttonEnabled(!isProgress)
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.genBtnRandom.isEnabled = isEnabled
    }
}