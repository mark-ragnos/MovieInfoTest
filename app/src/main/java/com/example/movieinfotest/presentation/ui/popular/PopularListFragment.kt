package com.example.movieinfotest.presentation.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.databinding.FragmentPopularListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieAdapter
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieLoadingStateAdapter
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularListFragment : BaseFragment() {
    private lateinit var binding: FragmentPopularListBinding
    private val viewModel: PopularViewModel by viewModels { AppViewModelFactory.makeFactory() }
    private val parentViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularListBinding.inflate(inflater, container, false)

        initToolbar()
        setupPopularList()
        fetchMovies()

        return binding.root
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
        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun addDivider() {
        binding.rvPopularList.addDefaultDivider(context, LinearLayout.VERTICAL)
    }
}
