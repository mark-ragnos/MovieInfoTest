package com.example.movieinfotest.presentation.ui.favourite

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
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.favourite.adapter.FavoriteAdapter
import com.example.movieinfotest.presentation.ui.favourite.adapter.FavoriteItemTouchCallback
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.getDivider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel
    private val parentViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        init()
        setupFavoriteList()
        fetchMovies()

        return binding.root
    }

    override fun onStart() {
        if (!FirebaseLogin.isLogin()) {
            binding.rvFavoriteList.visibility = View.GONE
            binding.favoriteTextLogin.visibility = View.VISIBLE
        }
        super.onStart()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory.makeFactory()
        ).get(FavoriteViewModel::class.java)

        binding.favoriteTextLogin.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_favoriteList_to_loginFragment)
        }

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
                        .navigate(R.id.action_favoriteList_to_loginFragment)
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
            viewModel.movies.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setupFavoriteList() {
        val listener = object : FavoriteAdapter.MovieDetailClickListener {
            override fun onClick(id: Int) {
                val action =
                    FavoriteListFragmentDirections.actionFavoriteListToMovieInfo(id)
                NavHostFragment.findNavController(this@FavoriteListFragment).navigate(action)
            }

            override fun onSwipe(id: Int) {
                viewModel.removeFromFavorite(id)
            }
        }
        adapter = FavoriteAdapter(listener)

        val touchHelper = ItemTouchHelper(FavoriteItemTouchCallback(adapter))
        touchHelper.attachToRecyclerView(binding.rvFavoriteList)

        binding.rvFavoriteList.adapter = adapter
        addDivider()
    }

    private fun addDivider() {
        context?.let {
            binding.rvFavoriteList.addItemDecoration(
                getDivider(
                    it,
                    LinearLayout.VERTICAL,
                    R.drawable.divider
                )
            )
        }
    }
}
