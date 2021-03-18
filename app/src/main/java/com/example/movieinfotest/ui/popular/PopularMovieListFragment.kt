package com.example.movieinfotest.ui.popular

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.paging.map
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.databinding.FragmentPopularMovieListBinding
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.ui.popular.adapter.MovieAdapter
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.ui.popular.adapter.MovieLoadingStateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PopularMovieListFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieListBinding
    private lateinit var viewModel: PopularViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMovieListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(PopularViewModel::class.java)

        setupUI()
        fetchMovies()

        return binding.root
    }

    private fun fetchMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavorite().collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupUI() {
        (activity as MainActivity).supportActionBar?.title =
            (activity as MainActivity).resources.getString(R.string.popular_title)
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
                        viewModel.saveInFavorite(movie)
            }
        }
        movieAdapter = MovieAdapter(listener, Repository.create())

        binding.rvPopularList.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter(movieAdapter)
        )
    }

}