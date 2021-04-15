package com.example.movieinfotest.presentation.ui.favourite

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
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.favourite.adapter.FavoriteAdapter
import com.example.movieinfotest.utils.FirebaseLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        init()
        setupFavoriteList()
        fetchMovies()

        return binding.root
    }

    private fun init(){
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(FavoriteViewModel::class.java)

        (activity as MainActivity).supportActionBar?.title =
            resources.getString(R.string.favorite_title)

        binding.favoriteTextLogin.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_favoriteList_to_loginFragment)
        }
    }

    private fun fetchMovies() {

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getPopular().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setupFavoriteList() {

        binding.rvFavoriteList.layoutManager = LinearLayoutManager(context)
        val listener = object : FavoriteAdapter.MovieDetailClickListener {
            override fun onClick(id: Int) {
                val action =
                    FavoriteListFragmentDirections.actionFavoriteListToMovieInfo(id)
                NavHostFragment.findNavController(this@FavoriteListFragment).navigate(action)
            }
        }
        adapter = FavoriteAdapter(listener)
        binding.rvFavoriteList.adapter = adapter
    }

    override fun onStart() {
        if(!FirebaseLogin.isLogin()){
            binding.rvFavoriteList.visibility = View.GONE
            binding.favoriteTextLogin.visibility = View.VISIBLE
        }
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}