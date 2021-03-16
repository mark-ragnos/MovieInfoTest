package com.example.movieinfotest.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.ui.favourite.adapter.MovieDetailDbAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: MovieDetailDbAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(FavoriteViewModel::class.java)
        (activity as MainActivity).supportActionBar?.title = (activity as MainActivity).resources.getString(R.string.favorite_title)

        setupUI()

        return binding.root
    }

    private fun setupUI(){
        val listener = object : MovieDetailDbAdapter.MovieDetailClickListener{
            override fun onClick(id: Int) {
                val action = FavoriteListFragmentDirections.actionFavoriteListToMovieInfo(id)
                NavHostFragment.findNavController(this@FavoriteListFragment).navigate(action)
            }
        }
        binding.rvPopularList.layoutManager = LinearLayoutManager(context)
        adapter = MovieDetailDbAdapter(listener)
        binding.rvPopularList.adapter = adapter
    }

//    private fun fetchMovies() {
//        CoroutineScope(Dispatchers.Main).launch {
//            viewModel.getFavorite().collectLatest { pagingData ->
//                movieAdapter.submitData(pagingData)
//            }
//        }
//    }
}