package com.example.movieinfotest.presentation.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.MainActivityViewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentGenerateMovieBinding
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.random.adapter.GenreAdapter
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

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
        initTextWatcher()
        initGenreList()
        setupUI()
        setupReadLifeData()

        return binding.root
    }

    private fun init() {
        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory.makeFactory()
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

    private fun initTextWatcher() {
        binding.genInYear.addTextChangedListener {
            buttonEnabled(isGenerateAccess(it.toString()))
        }

    }

    private fun setupUI() {
        binding.genBtnRandom.setOnClickListener {
            lifecycle.coroutineScope.launch(Dispatchers.Main) {
                onProgressGenerator(true)
                if (isGenerateAccess(binding.genInYear.text.toString()))
                    viewModel.generateRandom(
                        (binding.genInGenre.selectedItem as GenreDomain).id.toString(),
                        binding.genInYear.text.toString()
                    )
                else
                    onProgressGenerator(false)
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
            if (!viewModel.getGenres().isNullOrEmpty()) {
                genreAdapter = GenreAdapter(viewModel.getGenres()!!, requireContext())
                binding.genInGenre.adapter = genreAdapter
                buttonEnabled(NetworkConnection.isOnline())
            }
        }
    }

    private fun setupReadLifeData() {
        val detailObserver = Observer<MovieDomain> {
            setMovie(it)
        }
        viewModel.getRandom().observe(viewLifecycleOwner, detailObserver)
    }

    private fun setMovie(movie: MovieDomain) {
        binding.genOutPoster.registerImage(movie.poster_path, x = 150, y = 225)
        binding.genOutRating.text = movie.vote_average.toString()
        binding.genOutName.text = movie.title
        binding.genOutId.text = movie.id.toString()
        binding.genOutDesc.text = movie.overview
        onProgressGenerator(false)
    }

    private fun isGenerateAccess(inputYear: String): Boolean {
        if (!NetworkConnection.isOnline())
            return false

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        if (inputYear == "")
            return true
        if (inputYear.toInt() !in 1895..currentYear)
            return false

        return true
    }

    private fun onProgressGenerator(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.GONE
        binding.genResult.visibility = if (!isProgress) View.VISIBLE else View.INVISIBLE
        buttonEnabled(!isProgress)
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.genBtnRandom.isEnabled = isEnabled
    }
}
