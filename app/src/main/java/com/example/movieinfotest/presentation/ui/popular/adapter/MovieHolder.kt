package com.example.movieinfotest.presentation.ui.popular.adapter

import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.utils.registerImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    var favorite: ImageButton = binding.itemFavorite

    fun bind(movie: Movie?, listener: MovieAdapter.MovieClickListener) {
        binding.apply {
            movie?.let {
                itemId.text = it.id.toString()
                itemName.text = it.title
                itemRating.text = it.vote_average.toString()
                itemImage.registerImage(it.poster_path)
                itemView.setOnClickListener { listener.onClick(getId()) }
                favoriteRegister(it, listener)
            }
        }
    }

    private fun favoriteRegister(movie: Movie, listener: MovieAdapter.MovieClickListener) {
        favorite.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isFavorite = isFavorite(movie, listener)
                listener.onFavorite(movie, isFavorite)
                changeImage(!isFavorite)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val isFavorite = isFavorite(movie, listener)
            changeImage(isFavorite)
        }
    }

    private suspend fun isFavorite(
        item: Movie,
        listener: MovieAdapter.MovieClickListener
    ): Boolean {
        return listener.isFavorite(item.id)
    }

    private fun getId(): Int {
        return binding.itemId.text.toString().toInt()
    }

    fun changeImage(isFavorite: Boolean) {
        if (isFavorite)
            binding.itemFavorite.setImageResource(R.drawable.ic_favorite)
        else
            binding.itemFavorite.setImageResource(R.drawable.ic_favorite_not)
    }
}