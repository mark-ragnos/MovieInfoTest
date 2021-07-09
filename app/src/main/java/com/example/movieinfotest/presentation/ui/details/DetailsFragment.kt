package com.example.movieinfotest.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentDetailsBinding
import com.example.movieinfotest.domain.entities.actor.CastDomain
import com.example.movieinfotest.domain.entities.actor.CrewDomain
import com.example.movieinfotest.domain.entities.actor.asCast
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.register.RegistrationFragment
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.RATING_MULT
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.getGenreList
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.displayMoviePoster
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.setVisible
import com.example.movieinfotest.utils.setGone
import com.example.movieinfotest.utils.addDefaultDivider
import com.example.movieinfotest.utils.displayBackdrop
import com.example.movieinfotest.utils.listeners.NavigationListener
import com.example.movieinfotest.utils.setInvisible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels { AppViewModelFactory.getFactory(requireContext()) }
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val savedId = DetailsFragmentArgs.fromBundle(requireArguments()).id
        viewModel.sendID(savedId, NetworkConnection.getNetworkStatus(requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress(true)
        setupUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.infoAddToFavorite.setOnClickListener {
            if (FirebaseLogin.isLogin()) {
                saveDeleteMovie()
            } else {
                RegistrationFragment.navigate(NavHostFragment.findNavController(this))
            }
        }

        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetails.collectLatest { movie ->
                    movie?.let {
                        setMovie(movie)
                        progress(false)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavorite.collectLatest { isFavorite ->
                    changeFavoriteBtn(isInFavorite = isFavorite)
                }
            }
        }
    }

    private fun saveDeleteMovie() {
        if (!viewModel.isFavorite.value) {
            viewModel.saveInFavorite(NetworkConnection.getNetworkStatus(requireContext()))
        } else {
            viewModel.deleteFromFavorite()
        }
    }

    private fun setMovie(details: MovieDomain) {
        binding.infoDescription.text = details.overview
        binding.infoGenres.text = getGenreList(details.genres)
        binding.infoName.text =
            getString(R.string.title_with_date, details.title, details.releaseDate.getYear())
        binding.infoPoster.displayMoviePoster(details.posterPath, x = 100)
        binding.backdropImage.displayBackdrop(details.backdropPath)

        val realScore: Int = (details.voteAverage * RATING_MULT).toInt()
        binding.ratingBar.progress = realScore
        binding.ratingValue.text = getString(R.string.details_percents, realScore.toString())

        setActors(details.casts, details.crews)
    }

    private fun setActors(cast: List<CastDomain>?, crew: List<CrewDomain>?) {
        if (!cast.isNullOrEmpty()) {
            binding.castList.adapter = CastAdapter(cast, navigationClickListener)
            addDivider(binding.castList)
        }

        if (!crew.isNullOrEmpty()) {
            binding.crewList.adapter = CastAdapter(crew.asCast(), navigationClickListener)
            addDivider(binding.crewList)
        }

        doVisibleActorList()
    }

    private fun doVisibleActorList() {
        var counter = 0
        binding.castList.adapter?.let {
            binding.listOfSingleActors.setVisible()
            binding.castList.setVisible()
            counter++
        }
        binding.crewList.adapter?.let {
            if (counter == 0) {
                binding.crewList.setVisible()
                binding.castList.setGone()
            } else {
                binding.listOfSingleActors.setGone()
                binding.switchActors.setVisible()
                setupSwitchActors()
            }
            counter++
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

    private fun addDivider(list: RecyclerView) {
        list.addDefaultDivider(context, LinearLayout.HORIZONTAL)
    }

    private val navigationClickListener = object : NavigationListener<Int> {
        override fun navigate(param: Int) {
            val action = DetailsFragmentDirections.actionMovieInfoToActorFragment(param)
            NavHostFragment.findNavController(this@DetailsFragment).navigate(action)
        }
    }

    private fun changeFavoriteBtn(isInFavorite: Boolean) {
        if (isInFavorite) {
            binding.infoAddToFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.infoAddToFavorite.setImageResource(R.drawable.ic_favorite_not)
        }
    }

    private fun progress(isInProgress: Boolean) {
        if (isInProgress) {
            binding.progressBar.setVisible()
            binding.container.setInvisible()
        } else {
            binding.progressBar.setInvisible()
            binding.container.setVisible()
        }
    }
}
