package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.databinding.FragmentRandomMovieBinding
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.presentation.ui.random.adapter.RandomMoviesAdapter
import com.example.movieinfotest.utils.NOT_SELECTED_GENRE
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.isPossibleYear
import com.example.movieinfotest.utils.listeners.NavigationListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RandomMovieFragment : BaseFragment() {
    private var _binding: FragmentRandomMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RandomViewModel by viewModels {
        AppViewModelFactory.getFactory(
            requireContext()
        )
    }
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadGenres(NetworkConnection.getNetworkStatus(requireContext()))
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
        setupMovieList()
        setupGenres()
        setupButtons()
    }

    private fun initTextWatcher() {
        binding.genInYear.addTextChangedListener {
            buttonEnabled(isPossibleYear(it.toString()))
        }
    }

    private fun setupGenres() {
        val genreAdapter = GenreAdapter(
            index = if (NOT_SELECTED_GENRE == viewModel.selectedGenreId.value) 0 else viewModel.selectedGenreId.value,
            onSpinnerItemSelectedListener = { _, _, _, newItem ->
                viewModel.setSelectedGenre(newItem.id)
            },
            spinnerView = binding.genInGenre
        )
        binding.genInGenre.setSpinnerAdapter(genreAdapter)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genres.collectLatest { genres ->
                    if (genres.isNullOrEmpty()) {
                        return@collectLatest
                    }
                    genreAdapter.setItems(genres)
                }
            }
        }
    }

    private fun setupMovieList() {
        val adapter = RandomMoviesAdapter(navigationListener = object : NavigationListener<Int> {
            override fun navigate(param: Int) {
                val action = RandomMovieFragmentDirections.actionGenerateMovieToMovieInfo(param)
                NavHostFragment.findNavController(this@RandomMovieFragment).navigate(action)
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    adapter.addMovie(it)
                }
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addDefaultDivider(context, LinearLayout.VERTICAL)
    }

    private fun setupButtons() {
        binding.generate.setOnClickListener {
            viewModel.generateRandom(binding.genInYear.text.toString())
        }

        binding.clearFilter.setOnClickListener {
            viewModel.clearSelectedGenre()
        }
    }

    private fun initToolbar() {
        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.generate.isEnabled = isEnabled
    }
}
