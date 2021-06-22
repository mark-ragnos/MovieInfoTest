package com.example.movieinfotest.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentDetailsBinding
import com.example.movieinfotest.domain.entities.actor.CastDomain
import com.example.movieinfotest.domain.entities.actor.CrewDomain
import com.example.movieinfotest.domain.entities.actor.asCast
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.getGenreList
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.displayMoviePoster
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.setVisible
import com.example.movieinfotest.utils.setGone
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.listeners.NavigationListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels { AppViewModelFactory.makeFactory() }
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        onProgress(true)
        init()
        setupReadLifeData()
        setupFavoriteBtn()
        setupSwitchActors()

        return binding.root
    }

    private fun init() {
        val savedId = DetailsFragmentArgs.fromBundle(requireArguments()).id
        viewModel.sendID(savedId, NetworkConnection.getNetworkStatus(MovieApp.getInstance()))

        initToolbar()
    }

    private fun setupReadLifeData() {
        lifecycle.coroutineScope.launch {
            viewModel.movieDetails.collectLatest {
                it?.let { it1 -> setMovie(it1) }
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.isFavorite.collectLatest {
                changeFavoriteBnt(it)
            }
        }
    }

    private fun setupFavoriteBtn() {
        binding.infoAddToFavorite.setOnClickListener {
            if (FirebaseLogin.isLogin()) {
                saveDeleteMovie()
            } else {
                moveToLogin()
            }
        }
    }

    private fun setupSwitchActors() {
        binding.switchActors.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.crewList.setVisible()
                binding.castList.setGone()
            } else {
                binding.crewList.setGone()
                binding.castList.setVisible()
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun saveDeleteMovie() {
        if (!viewModel.isFavorite.value) {
            viewModel.saveInFavorite(NetworkConnection.getNetworkStatus(MovieApp.getInstance()))
        } else {
            viewModel.deleteFromFavorite()
        }
    }

    private fun moveToLogin() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_global_registrationGraph)
    }

    private fun changeFavoriteBnt(isFavorite: Boolean) {
        if (isFavorite) {
            binding.infoAddToFavorite.text =
                resources.getText(R.string.delete_from_favorite)
        } else {
            binding.infoAddToFavorite.text = resources.getText(R.string.save_as_favorite)
        }
    }

    private fun setMovie(details: MovieDomain) {
        binding.infoDescription.text = details.overview
        binding.infoGenres.text = getGenreList(details.genres)
        binding.infoName.text =
            getString(R.string.title_with_date, details.title, details.releaseDate.getYear())
        binding.infoRating.text = details.voteAverage.toString()
        binding.infoPoster.displayMoviePoster(details.posterPath, x = 150, y = 225)
        setActors(details.casts, details.crews)
        onProgress(false)
    }

    private fun setActors(cast: List<CastDomain>?, crew: List<CrewDomain>?) {
        if (!cast.isNullOrEmpty()) {
            binding.actors.setVisible()

            binding.castList.adapter = CastAdapter(cast, navigationClickListener)
            addDivider(binding.castList)
        }

        if (!crew.isNullOrEmpty()) {
            binding.actors.setVisible()

            binding.crewList.adapter = CastAdapter(crew.asCast(), navigationClickListener)
            addDivider(binding.crewList)
        }
    }

    private fun addDivider(list: RecyclerView) {
        list.addDefaultDivider(context, LinearLayout.HORIZONTAL)
    }

    private fun onProgress(isVisible: Boolean) {
        if (isVisible) {
            binding.progressBar.setVisible()
            binding.scrollView.setGone()
        } else {
            binding.progressBar.setGone()
            binding.scrollView.setVisible()
        }
    }

    private val navigationClickListener = object : NavigationListener<Int> {
        override fun navigate(param: Int) {
            val action = DetailsFragmentDirections.actionMovieInfoToActorFragment(param)
            NavHostFragment.findNavController(this@DetailsFragment).navigate(action)
        }
    }
}
