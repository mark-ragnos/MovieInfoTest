package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.databinding.FragmentRandomMovieBinding
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.random.adapter.RandomMoviesAdapter
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.isPossibleYear
import com.example.movieinfotest.utils.launchAndRepeatOnLifecycle
import com.example.movieinfotest.utils.listeners.NavigationListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

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
        launchAndRepeatOnLifecycle {
            viewModel.genres.collectLatest { genres ->
                if (genres.isNullOrEmpty()) {
                    return@collectLatest
                }

                val genresText = genres.map { it.name }
                val adapter = ArrayAdapter(requireContext(), R.layout.item_genres, genresText)
                binding.genreSelected.setAdapter(adapter)
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

        launchAndRepeatOnLifecycle {
            viewModel.movies.collect {
                adapter.addMovie(it)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addDefaultDivider(context, LinearLayout.VERTICAL)
    }

    private fun setupButtons() {
        binding.generate.setOnClickListener {
            viewModel.generateRandom(
                binding.genInYear.text.toString(),
                binding.genreSelected.text.toString()
            )
        }

        binding.clearFilter.setOnClickListener {
            binding.genreSelected.setText("")
            binding.genInYear.setText("")
        }
    }

    private fun initToolbar() {
        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.generate.isEnabled = isEnabled
    }
}
