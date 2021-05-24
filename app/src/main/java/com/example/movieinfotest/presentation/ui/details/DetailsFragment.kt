package com.example.movieinfotest.presentation.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentMovieInfoBinding
import com.example.movieinfotest.domain.entities.actor.ActorDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.details.actors.ActorAdapter
import com.example.movieinfotest.utils.*
import com.example.movieinfotest.utils.network.NetworkConnection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieInfoBinding
    private lateinit var viewModel: DetailsViewModel
    private val parentViewModel: MainActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        onProgress(true)
        init()
        setupReadLifeData()
        setupFavoriteBtn()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.scrollView.scrollTo(0, 0)
    }

    private fun init() {
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory.makeFactory()
        ).get(DetailsViewModel::class.java)

        val savedId = DetailsFragmentArgs.fromBundle(requireArguments()).id
        viewModel.sendID(savedId, NetworkConnection.getNetworkStatus(MovieApp.getInstance()))

        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

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
                        .navigate(R.id.action_movieInfo_to_loginFragment)
                }

                R.id.logout -> {
                    parentViewModel.auth.signOut()
                    parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
                }
            }
            return@setOnMenuItemClickListener true
        }
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
            if (FirebaseLogin.isLogin())
                saveDeleteMovie()
            else
                moveToLogin()
        }
    }

    private fun saveDeleteMovie() {
        if (!viewModel.isFavorite.value) {
            makeToast(resources.getString(R.string.movie_added_to_favorite))
            viewModel.saveInFavorite(NetworkConnection.getNetworkStatus(MovieApp.getInstance()))
        } else {
            makeToast(resources.getString(R.string.movie_deleted_from_favorite))
            viewModel.deleteFromFavorite()
        }
    }

    private fun moveToLogin() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_movieInfo_to_loginFragment)
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
        binding.infoDate.text = details.releaseDate.getYear()
        binding.infoDescription.text = details.overview
        binding.infoGenres.text = getGenreList(details.genres)
        binding.infoName.text = details.title
        binding.infoRating.text = details.voteAverage.toString()
        binding.infoPoster.registerImage(details.posterPath, x = 150, y = 225)
        setActors(details.actors)
        onProgress(false)
    }

    private fun setActors(list: List<ActorDomain>?) {
        if (!list.isNullOrEmpty()) {
            binding.lvActors.adapter = ActorAdapter(list)
        } else {
            binding.lvActors.visibility = View.INVISIBLE
            binding.infoTextActors.visibility = View.INVISIBLE
            binding.infoTextGenres.visibility = View.INVISIBLE
        }
    }

    private fun makeToast(text: String) {
        context?.let { ToastUtils.makeShortMessage(it, text) }
    }

    private fun onProgress(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.scrollView.visibility = if (!isVisible) View.VISIBLE else View.INVISIBLE
    }
}
