package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.Observable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentGenerateMovieBinding
import com.example.movieinfotest.domain.entities.genre.Genre
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.utils.network.NetworkStatus
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToastUtils
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class RandomMovieFragment : Fragment() {
    private lateinit var binding: FragmentGenerateMovieBinding
    private lateinit var viewModel: RandomViewModel
    private lateinit var genreAdapter: GenreAdapter
    private val parentViewModel: MainActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateMovieBinding.inflate(inflater, container, false)

        init()
        initGenreList()
        initButtonObserver()
        initTextWatcher()
        setupUI()
        setupReadLifeData()

        return binding.root
    }

    private fun init() {
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(RandomViewModel::class.java)

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
                        .navigate(R.id.action_generateMovie_to_loginFragment)
                }

                R.id.logout -> {
                    parentViewModel.auth.signOut()
                    parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun initButtonObserver() {
        viewModel.buttonEnabled.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                buttonEnabled(viewModel.buttonEnabled.get())
            }
        })
    }

    private fun initTextWatcher() {
        binding.genInYear.addTextChangedListener {
            viewModel.buttonEnabled.set(isGenerateAccess(it.toString()))
        }

    }

    private fun setupUI() {
        binding.genBtnRandom.setOnClickListener {
            lifecycle.coroutineScope.launch(Dispatchers.Main) {
                onProgress(true)
                viewModel.generateRandom(
                    (binding.genInGenre.selectedItem as Genre).id.toString(),
                    binding.genInYear.text.toString()
                )
            }
        }

        binding.genResult.setOnClickListener {
            val action = RandomMovieFragmentDirections.actionGenerateMovieToMovieInfo(
                binding.genOutId.text.toString().toInt()
            )
            NavHostFragment.findNavController(this).navigate(action)

        }

    }

    private fun initGenreList() {
        lifecycle.coroutineScope.launch(Dispatchers.Main) {
            if (viewModel.getGenres() != null) {
                viewModel.getGenres()?.let {
                    genreAdapter = GenreAdapter(it, requireContext())
                    binding.genInGenre.adapter = genreAdapter
                }
                viewModel.buttonEnabled.set(true)
            }
        }
    }

    private fun setupReadLifeData() {
        val detailObserver = Observer<Movie> {
            setMovie(it)
        }
        viewModel.getRandom().observe(viewLifecycleOwner, detailObserver)
    }

    private fun setMovie(movie: Movie) {
        binding.genOutPoster.registerImage(movie.poster_path, x = 150, y = 225)
        binding.genOutRating.text = movie.vote_average.toString()
        binding.genOutName.text = movie.title
        binding.genOutId.text = movie.id.toString()
        binding.genOutDesc.text = movie.overview
        onProgress(false)
    }

    private fun isGenerateAccess(inputYear: String): Boolean {
        if (NetworkConnection.isOnline() == NetworkStatus.OFFLINE) {
            return false
        }
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        if (inputYear == "")
            return true

        if (inputYear.toInt() !in 1895..currentYear) {
            return false
        }

        return true
    }

    private fun makeMessage(message: CharSequence) {
        context?.let { ToastUtils.makeShortMessage(it, message.toString()) }
    }

    private fun onProgress(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.GONE
        binding.genResult.visibility = if (!isProgress) View.VISIBLE else View.INVISIBLE
        viewModel.buttonEnabled.set(!isProgress)
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.genBtnRandom.isEnabled = isEnabled
    }
}