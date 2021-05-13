package com.example.movieinfotest.presentation.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentPopularMovieListBinding
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieAdapter
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieLoadingStateAdapter
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PopularMovieListFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieListBinding
    private lateinit var viewModel: PopularViewModel
    private val parentViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMovieListBinding.inflate(inflater, container, false)

        init()
        setupPopularList()
        fetchMovies()

        return binding.root
    }

    private fun init() {
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(PopularViewModel::class.java)

        initToolbar()
    }

    private fun initToolbar() {
        ToolbarMaker.makeToolbar(binding.toolbar, parentViewModel)
        initMenuItemClickListener()
    }

    private fun initMenuItemClickListener() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.dark_mode_btn -> {
                    parentViewModel.changeDarkMode()
                }

                R.id.login -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_popularMovieList_to_loginFragment)
                }

                R.id.logout -> {
                    parentViewModel.auth.signOut()
                    parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun fetchMovies() {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            viewModel.getFavorite().collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupPopularList() {
        binding.rvPopularList.layoutManager = LinearLayoutManager(context)

        val listener = object : MovieAdapter.MovieClickListener {
            override fun onClick(id: Int) {
                val action =
                    PopularMovieListFragmentDirections.actionPopularMovieListToMovieInfo(id)
                NavHostFragment.findNavController(this@PopularMovieListFragment).navigate(action)
            }

            override fun onFavorite(movie: Movie?, isFavorite: Boolean) {
                if (movie != null)
                    if (isFavorite)
                        viewModel.removeFromFavorite(movie)
                    else
                        viewModel.saveInFavorite(
                            movie,
                            NetworkConnection.isOnline()
                        )
            }

            override suspend fun isFavorite(id: Int): Boolean {
                return viewModel.isFavorite(id)
            }
        }
        movieAdapter = MovieAdapter(listener)

        binding.rvPopularList.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter(movieAdapter)
        )
    }

}