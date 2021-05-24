package com.example.movieinfotest.presentation.ui.popular.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieAdapter(
    private val listener: MovieClickListener,
    private val isLogin: Boolean = false
) :
    PagingDataAdapter<MovieDomain, MovieHolder>(MovieDiffCallback) {
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movie = getItem(position), listener)

        holder.favorite.visibility = if (isLogin) View.VISIBLE else View.INVISIBLE

        holder.favorite.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val isFavorite = getItem(position)?.let { it1 -> isFavorite(it1) } ?: false
                listener.onFavorite(getItem(position), isFavorite)
                holder.changeImage(!isFavorite)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val isFavorite = getItem(position)?.let { it1 -> isFavorite(it1) } ?: false
            holder.changeImage(isFavorite)
        }
    }

    interface MovieClickListener {
        fun onClick(id: Int)
        fun onFavorite(movie: MovieDomain?, isFavorite: Boolean)
        suspend fun isFavorite(id: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    private suspend fun isFavorite(item: MovieDomain): Boolean {
        return listener.isFavorite(item.id)
    }
}
