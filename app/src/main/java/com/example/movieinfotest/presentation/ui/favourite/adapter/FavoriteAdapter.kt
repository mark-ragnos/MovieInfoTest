package com.example.movieinfotest.presentation.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.Movie
import com.example.movieinfotest.utils.registerImage

class FavoriteAdapter(
    private val listener: MovieDetailClickListener
) : PagingDataAdapter<Movie, FavoriteAdapter.MovieDetailsDbHolder>(MovieDetailsDiffCallback) {

    interface MovieDetailClickListener {
        fun onClick(id: Int)
    }

    object MovieDetailsDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class MovieDetailsDbHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?){
            movie?.let {
                binding.itemName.text = movie.title
                binding.itemId.text = movie.id.toString()
                binding.itemRating.text = movie.vote_average.toString()
                binding.itemImage.registerImage(movie.poster_path)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieDetailsDbHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            getItem(position)?.id?.let {
                listener.onClick(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsDbHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieDetailsDbHolder(binding)
    }
}