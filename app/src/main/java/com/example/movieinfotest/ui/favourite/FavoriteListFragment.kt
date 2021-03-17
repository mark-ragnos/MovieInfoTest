package com.example.movieinfotest.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.ui.favourite.adapter.PopularAdapter
import com.example.movieinfotest.utils.MovieFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(FavoriteViewModel::class.java)

        (activity as MainActivity).supportActionBar?.title =
            resources.getString(R.string.favorite_title)

        setupUI()

        return binding.root
    }

    private fun setupReadLifeData(){
        val detailObserver = Observer<List<MovieDetailsDB>> {
            adapter.notifyDataSetChanged()
        }
        viewModel.getMovies().observe(viewLifecycleOwner, detailObserver)
    }

    private fun setupUI() {
        val listener = object : PopularAdapter.MovieDetailClickListener {
            override fun onClick(id: Int) {
                val action = FavoriteListFragmentDirections.actionFavoriteListToMovieInfo(id)
                NavHostFragment.findNavController(this@FavoriteListFragment).navigate(action)
            }
        }
        binding.rvPopularList.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.Main).launch {
            if (viewModel.getFavoirte() != null) {
                viewModel.loadMovies()
                adapter = PopularAdapter(viewModel.getMovies(), listener)
                binding.rvPopularList.adapter = adapter
                setupReadLifeData()
            } else {
                Toast.makeText(context, "Empty favorite list", Toast.LENGTH_SHORT).show()
            }
        }
    }

}