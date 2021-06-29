package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.databinding.FragmentRandomMovieBinding
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.presentation.ui.random.adapter.RandomMoviesAdapter
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.isPossibleYear
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RandomMovieFragment : BaseFragment() {
    private lateinit var binding: FragmentRandomMovieBinding
    private val viewModel: RandomViewModel by viewModels { AppViewModelFactory.makeFactory() }
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var genreAdapter: GenreAdapter
    private val randomAdapter = RandomMoviesAdapter()

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
        binding.recyclerView.adapter = randomAdapter

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

        viewModel.loadGenres(NetworkConnection.getNetworkStatus(MovieApp.getInstance()))
    }

    private fun setupUI() {
        binding.genBtnRandom.setOnClickListener {
            if (isGenerateAccess()) {
                viewModel.generateRandom(binding.genInYear.text.toString())
            }
        }
    }

    private fun subscribeOnData() {
        lifecycle.coroutineScope.launch {
            viewModel.genres.collectLatest {
                it?.let { genreAdapter.setItems(it) }
            }
        }

        viewModel.movies.onEach {
            randomAdapter.addMovie(it)
        }.launchIn(lifecycleScope)
    }

    private fun initToolbar() {
        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun isGenerateAccess(): Boolean {
        return NetworkConnection.isOnline()
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.genBtnRandom.isEnabled = isEnabled
    }
}
