package com.example.movieinfotest.presentation.ui.popular.adapter


import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.utils.registerImage

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
            }
        }
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