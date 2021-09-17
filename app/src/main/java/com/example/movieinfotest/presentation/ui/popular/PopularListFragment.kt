package com.example.movieinfotest.presentation.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.databinding.FragmentPopularListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieAdapter
import com.example.movieinfotest.presentation.ui.popular.adapter.MovieLoadingStateAdapter
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.launchAndRepeatOnLifecycle
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.collectLatest

class PopularListFragment : BaseFragment() {
    private var _binding: FragmentPopularListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PopularViewModel by viewModels {
        AppViewModelFactory.getFactory(
            requireContext()
        )
    }
    private val parentViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (_binding == null) return

        initToolbar()
        setupPopularList()
    }

    private fun setupPopularList() {
        val listener = object : MovieAdapter.MovieClickListener {
            override fun navigate(param: Int) {
                val action =
                    PopularListFragmentDirections.actionPopularMovieListToMovieInfo(param)
                NavHostFragment.findNavController(this@PopularListFragment).navigate(action)
            }

            override fun favoriteAddOrRemove(movie: MovieDomain, isFavorite: Boolean) {
                if (isFavorite) {
                    viewModel.removeFromFavorite(movie)
                } else {
                    viewModel.saveInFavorite(
                        movie,
                        NetworkConnection.getNetworkStatus(requireContext())
                    )
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

    private fun fetchData() {
        launchAndRepeatOnLifecycle {
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
