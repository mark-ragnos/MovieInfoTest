package com.example.movieinfotest.presentation.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.favourite.adapter.FavoriteAdapter
import com.example.movieinfotest.presentation.ui.favourite.adapter.FavoriteItemTouchCallback
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.presentation.ui.register.RegistrationFragment
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.launchAndRepeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest

class FavoriteListFragment : BaseFragment() {
    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels {
        AppViewModelFactory.getFactory(
            requireContext()
        )
    }
    private val parentViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupFavoriteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        displayList()
    }

    private fun setupUI() {
        binding.favoriteTextLogin.setOnClickListener {
            RegistrationFragment.navigate(NavHostFragment.findNavController(this))
        }

        initToolbar()
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

    private fun fetchData() {
        launchAndRepeatOnLifecycle {
            viewModel.movies.collectLatest { pageMovies ->
                adapter.submitData(pageMovies)
            }
        }
    }

    private fun displayList() {
        if (!FirebaseLogin.isLogin()) {
            binding.rvFavoriteList.visibility = View.GONE
            binding.favoriteTextLogin.visibility = View.VISIBLE
        }
    }

    private fun initToolbar() {
        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun addDivider() {
        binding.rvFavoriteList.addDefaultDivider(context, LinearLayout.VERTICAL)
    }
}
