package com.example.movieinfotest.presentation.ui.popular.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.domain.usecases.FavoriteMovieUseCase
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.getYear
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieAdapter(
    private val listener: MovieClickListener,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) :
    PagingDataAdapter<Movie, MovieHolder>(MovieDiffCallback) {
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movie = getItem(position), listener)

        if (FirebaseLogin.isLogin())
            holder.favorite.visibility = View.VISIBLE

        holder.favorite.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val isFavorite = isFavorite(position)
                listener.onFavorite(getItem(position), isFavorite)
                holder.changeImage(!isFavorite)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val isFavorite = isFavorite(position)
            holder.changeImage(isFavorite)
        }
    }

    interface MovieClickListener {
        fun onClick(id: Int)
        fun onFavorite(movie: Movie?, isFavorite: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    private suspend fun isFavorite(position: Int): Boolean = withContext(Dispatchers.IO) {
        return@withContext favoriteMovieUseCase.isFavorite(getItem(position)!!.id)

    }
}