package com.example.movieinfotest.presentation.ui.popular

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentPopularMovieListBinding
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieAdapter
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieLoadingStateAdapter
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch


class PopularMovieListFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieListBinding
    private lateinit var viewModel: PopularViewModel
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

        (activity as MainActivity).supportActionBar?.title =
            (activity as MainActivity).resources.getString(R.string.popular_title)
    }

    private fun fetchMovies() {
        lifecycle.coroutineScope.launch(Dispatchers.IO){
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
                        viewModel.saveInFavorite(movie, NetworkConnection.isOnline(MovieApp.getInstance()))
            }

            override suspend fun isFavorite(id: Int):Boolean {
                return viewModel.isFavorite(id)
            }
        }
        movieAdapter = MovieAdapter(listener)

        binding.rvPopularList.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter(movieAdapter)
        )
    }

}