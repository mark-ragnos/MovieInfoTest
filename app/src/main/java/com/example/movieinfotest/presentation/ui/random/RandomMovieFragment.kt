package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.databinding.FragmentRandomMovieBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.displayMoviePoster
import com.example.movieinfotest.utils.isPossibleYear
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RandomMovieFragment : BaseFragment() {
    private lateinit var binding: FragmentRandomMovieBinding
    private val viewModel: RandomViewModel by viewModels { AppViewModelFactory.getFactory(requireContext()) }
    private lateinit var genreAdapter: GenreAdapter
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomMovieBinding.inflate(inflater, container, false)

        initToolbar()
        initTextWatcher()
        initSpinner()
        setupUI()
        subscribeOnData()

        return binding.root
    }

    private fun initTextWatcher() {
        binding.genInYear.addTextChangedListener {
            buttonEnabled(isPossibleYear(it.toString()))
        }
    }

    private fun initSpinner() {
        genreAdapter = GenreAdapter(0, null, binding.genInGenre)

        binding.genInGenre.setSpinnerAdapter(genreAdapter)
        binding.genInGenre.setOnSpinnerItemSelectedListener(viewModel.selectGenreListener)
        binding.genInGenre.lifecycleOwner = viewLifecycleOwner

        viewModel.loadGenres(NetworkConnection.getNetworkStatus(requireContext()))
    }

    private fun setupUI() {
        binding.genBtnRandom.setOnClickListener {
            if (isGenerateAccess()) {
                onProgressGenerator(true)
                viewModel.generateRandom(binding.genInYear.text.toString())
            }
        }

        binding.cardView.setOnClickListener {
            val action = RandomMovieFragmentDirections.actionGenerateMovieToMovieInfo(
                binding.genOutId.text.toString().toInt()
            )
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun subscribeOnData() {
        lifecycle.coroutineScope.launch {
            viewModel.movie.collectLatest {
                it?.let { setMovie(it) }
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.genres.collectLatest {
                it?.let { genreAdapter.setItems(it) }
            }
        }
    }

    private fun initToolbar() {
        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun setMovie(movie: MovieDomain) {
        binding.apply {
            genOutPoster.displayMoviePoster(movie.posterPath, x = 150, y = 225)
            genOutRating.text = movie.voteAverage.toString()
            genOutName.text = movie.title
            genOutId.text = movie.id.toString()
            genOutDesc.text = movie.overview
        }
        onProgressGenerator(false)
    }

    private fun isGenerateAccess(): Boolean {
        return NetworkConnection.isOnline(requireContext())
    }

    private fun onProgressGenerator(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.GONE
        binding.cardView.visibility = if (!isProgress) View.VISIBLE else View.INVISIBLE
        buttonEnabled(!isProgress)
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.genBtnRandom.isEnabled = isEnabled
    }
}
