package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.flow.toCollection
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

    private val navigationListener = object : NavigationListener<Int> {
        override fun navigate(param: Int) {
            val action = RandomMovieFragmentDirections.actionGenerateMovieToMovieInfo(param)
            NavHostFragment.findNavController(this@RandomMovieFragment).navigate(action)
        }
    }

    private var genreAdapter: GenreAdapter? = null
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genres.collect {
                    it?.let {
                        genreAdapter?.setItems(it)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    Log.d("TEST", "Collect movie $it in $this")
                    randomAdapter.addMovie(it)
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedGenreId.collect {
                    if (it == NOT_SELECTED_GENRE) {
                        binding.genInGenre.clearSelectedItem()
                        binding.genInYear.setText("")
                    }
                }
            }
        }
    }

    private fun initTextWatcher() {
        binding.genInYear.addTextChangedListener {
            buttonEnabled(isPossibleYear(it.toString()))
        }
    }

    private fun initSpinner() {
        genreAdapter =
            if (genreAdapter == null) GenreAdapter(
                0,
                viewModel.selectGenreListener,
                binding.genInGenre
            ) else genreAdapter

        binding.genInGenre.setSpinnerAdapter(genreAdapter!!)
        binding.genInGenre.lifecycleOwner = viewLifecycleOwner

        viewModel.loadGenres(NetworkConnection.getNetworkStatus(requireContext()))
    }

    private fun setupUI() {
        binding.generate.setOnClickListener {
            viewModel.generateRandom(binding.genInYear.text.toString())
        }

        binding.clearFilter.setOnClickListener {
            viewModel.clearSelectedGenre()
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
