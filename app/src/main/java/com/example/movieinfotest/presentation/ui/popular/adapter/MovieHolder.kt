package com.example.movieinfotest.presentation.ui.popular.adapter

import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.displayMoviePoster

class MovieHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    var favorite: ImageButton = binding.itemFavorite

    fun bind(movie: MovieDomain?, listener: MovieAdapter.MovieClickListener) {
        binding.apply {
            movie?.let {
                itemId.text = it.id.toString()
                itemName.text = it.title
                itemRating.text = it.voteAverage.toString()
                itemImage.displayMoviePoster(it.posterPath, SIZE_X, SIZE_Y)
                itemView.setOnClickListener { listener.onClick(getId()) }
            }
        }
    }

    private fun getId(): Int {
        return binding.itemId.text.toString().toInt()
    }

    fun changeImage(isFavorite: Boolean) {
        if (isFavorite) {
            binding.itemFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.itemFavorite.setImageResource(R.drawable.ic_favorite_not)
        }
    }

    companion object {
        private const val SIZE_X = 75
        private const val SIZE_Y = 100
    }
}
