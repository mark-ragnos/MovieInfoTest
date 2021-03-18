package com.example.movieinfotest.ui.details

import android.os.Bundle
import android.util.Log
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
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentMovieInfoBinding
import com.example.movieinfotest.models.actors.Actor
import com.example.movieinfotest.models.details.MovieDetails
import com.example.movieinfotest.ui.AppViewModelFactory
import com.example.movieinfotest.ui.details.actors.ActorAdapter
import com.example.movieinfotest.utils.MovieFrom
import com.example.movieinfotest.utils.getGenreList
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieInfoBinding
    private lateinit var viewModel: DetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        init()
        setupReadLifeData()
        setupFavoriteBtn()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun init() {
        //get ID
        val saved = DetailsFragmentArgs.fromBundle(requireArguments()).id
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.sendID(saved)
        }

        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(DetailsViewModel::class.java)

        (activity as MainActivity).supportActionBar?.title =
            resources.getString(R.string.details_title)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupReadLifeData() {
        val detailObserver = Observer<MovieFrom<MovieDetails?>> {
            if (it.movie != null)
                setMovie(it.movie)
            changeFavoriteBnt()
        }
        viewModel.getDetails().observe(viewLifecycleOwner, detailObserver)


        val actorObserver = Observer<List<Actor>> {
            setActors(it)
        }
        viewModel.getActors().observe(viewLifecycleOwner, actorObserver)
    }

    private fun setupFavoriteBtn() {
        binding.infoAddToFavorite.setOnClickListener {
            val login = (activity as MainActivity).isLogin()

            if (!login) {
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_movieInfo_to_loginFragment)
                return@setOnClickListener
            }

            if (!MainActivity.isLogin())
                if (!viewModel.isFavorite()) {
                    makeToast(resources.getString(R.string.movie_added_to_favorite))
                    viewModel.saveInFavorite()
                } else {
                    makeToast(resources.getString(R.string.movie_deleted_from_favorite))
                    viewModel.deleteFromFavorite()
                }

            viewModel.changeIsFavorite()
            changeFavoriteBnt()
        }

    }

    private fun changeFavoriteBnt() {
        if (!MainActivity.isLogin())
            if (viewModel.isFavorite()) {
                binding.infoAddToFavorite.text = resources.getText(R.string.delete_from_favorite)
            } else {
                binding.infoAddToFavorite.text = resources.getText(R.string.save_as_favorite)
            }
    }

    private fun setMovie(details: MovieDetails) {
        binding.infoDate.text = details.release_date.getYear()
        binding.infoDescription.text = details.overview
        binding.infoGenres.text = getGenreList(details.genres)
        binding.infoName.text = details.title
        binding.infoRating.text = details.vote_average.toString()
        binding.infoPoster.registerImage(details.poster_path, x = 150, y = 225)
    }

    private fun setActors(list: List<Actor>?) {
        if (list != null) {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL

            binding.lvActors.layoutManager = manager
            binding.lvActors.adapter = ActorAdapter(list)
        } else {
            binding.lvActors.visibility = View.INVISIBLE
            binding.infoTextActors.visibility = View.INVISIBLE
            binding.infoTextGenres.visibility = View.INVISIBLE
            binding.infoGenres.visibility = View.INVISIBLE
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}