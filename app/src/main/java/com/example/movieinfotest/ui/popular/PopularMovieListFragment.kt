package com.example.movieinfotest.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentFavoriteListBinding
import com.example.movieinfotest.listadapter.MovieAdapter
import com.example.movieinfotest.ui.AppViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PopularMovieListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: PopularViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)


        viewModel = ViewModelProviders.of(
            this,
            AppViewModelFactory()
        ).get(PopularViewModel::class.java)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        //Adapter settings
        binding.rvPopularList.layoutManager = LinearLayoutManager(context)
        binding.rvPopularList.adapter = MovieAdapter()

        binding.rvPopularList.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("ID", it.findViewById<TextView>(R.id.item_id).text.toString().toInt())
            NavHostFragment.findNavController(this).navigate(R.id.action_popularMovieList_to_movieInfo, bundle)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavorite().collectLatest {
                (binding.rvPopularList.adapter as MovieAdapter).submitData(it)
            }
        }
    }
}