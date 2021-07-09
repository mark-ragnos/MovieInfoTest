package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.databinding.FragmentRandomMovieBinding
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.presentation.ui.random.adapter.RandomMoviesAdapter
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.isPossibleYear
import com.example.movieinfotest.utils.listeners.NavigationListener
import kotlinx.coroutines.flow.collectLatest

class RandomMovieFragment : BaseFragment() {
    private var _binding: FragmentRandomMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RandomViewModel by viewModels { AppViewModelFactory.makeFactory() }
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    private val navigationListener = object : NavigationListener<Int> {
        override fun navigate(param: Int) {
            val action = RandomMovieFragmentDirections.actionGenerateMovieToMovieInfo(param)
            NavHostFragment.findNavController(this@RandomMovieFragment).navigate(action)
        }
    }

    private lateinit var genreAdapter: GenreAdapter
    private val randomAdapter = RandomMoviesAdapter(navigationListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeOnData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initTextWatcher()
        initSpinner()
        setupUI()
    }

    private fun subscribeOnData() {
        lifecycle.coroutineScope.launchWhenResumed {
            viewModel.genres.collectLatest {
                it?.let { genreAdapter.setItems(it) }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.movies.collectLatest {
                randomAdapter.addMovie(it)
                binding.recyclerView.smoothScrollToPosition(0)
            }
        }
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
        binding.generate.setOnClickListener {
            viewModel.generateRandom(binding.genInYear.text.toString())
        }

        binding.recyclerView.adapter = randomAdapter
        binding.recyclerView.addDefaultDivider(context, LinearLayout.VERTICAL)
    }

    private fun initToolbar() {
        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.generate.isEnabled = isEnabled
    }
}
