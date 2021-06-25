package com.example.movieinfotest.presentation.ui.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.listeners.NavigationListener

class MovieAdapter(
    private val listener: MovieClickListener,
    private val isLogin: Boolean = false
) :
    PagingDataAdapter<MovieDomain, MovieHolder>(MovieDiffCallback) {
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movie = getItem(position))
    }

    interface MovieClickListener : NavigationListener<Int> {
        fun favoriteAddOrRemove(movie: MovieDomain, isFavorite: Boolean)
        suspend fun isFavorite(id: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding, listener, isLogin)
    }
}
