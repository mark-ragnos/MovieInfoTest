package com.example.movieinfotest.presentation.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentPopularListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieAdapter
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieLoadingStateAdapter
import com.example.movieinfotest.presentation.ui.register.RegistrationFragment
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.getDivider
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.reRunFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularListFragment : Fragment() {
    private lateinit var binding: FragmentPopularListBinding
    private lateinit var viewModel: PopularViewModel
    private val parentViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularListBinding.inflate(inflater, container, false)

        init()
        setupPopularList()
        fetchMovies()

        return binding.root
    }

    private fun init() {
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory.makeFactory()
        ).get(PopularViewModel::class.java)

        initToolbar()
    }

    private fun setupPopularList() {
        val listener = object : MovieAdapter.MovieClickListener {
            override fun onClick(id: Int) {
                val action =
                    PopularListFragmentDirections.actionPopularMovieListToMovieInfo(id)
                NavHostFragment.findNavController(this@PopularListFragment).navigate(action)
            }

            override fun onFavorite(movie: MovieDomain?, isFavorite: Boolean) {
                movie?.let {
                    if (isFavorite) {
                        viewModel.removeFromFavorite(it)
                    } else {
                        viewModel.saveInFavorite(
                            movie,
                            NetworkConnection.getNetworkStatus(MovieApp.getInstance())
                        )
                    }
                }
            }

            override suspend fun isFavorite(id: Int): Boolean {
                return viewModel.isFavorite(id)
            }
        }
        movieAdapter = MovieAdapter(listener, FirebaseLogin.isLogin())

        binding.rvPopularList.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter(movieAdapter)
        )
        addDivider()
    }

    private fun fetchMovies() {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            viewModel.movies.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
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
                    RegistrationFragment.navigate(NavHostFragment.findNavController(this))
                }

                R.id.logout -> {
                    parentViewModel.auth.signOut()
                    parentFragmentManager.reRunFragment(this)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun addDivider() {
        context?.let {
            binding.rvPopularList.addItemDecoration(
                getDivider(
                    it,
                    LinearLayout.VERTICAL,
                    R.drawable.divider
                )
            )
        }
    }
}
