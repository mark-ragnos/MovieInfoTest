package com.example.movieinfotest.presentation.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.databinding.ItemListBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.displayMoviePoster

class FavoriteAdapter(
    private val listener: MovieDetailClickListener
) : PagingDataAdapter<MovieDomain, FavoriteAdapter.MovieDetailsDbHolder>(MovieDetailsDiffCallback),
    FavoriteItemTouchListener {

    interface MovieDetailClickListener {
        fun onClick(id: Int)
        fun onSwipe(id: Int)
    }

    object MovieDetailsDiffCallback : DiffUtil.ItemCallback<MovieDomain>() {
        override fun areItemsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
            return oldItem == newItem
        }
    }

    class MovieDetailsDbHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDomain?) {
            movie?.let {
                binding.itemName.text = movie.title
                binding.itemId.text = movie.id.toString()
                binding.itemRating.text = movie.voteAverage.toString()
                binding.itemImage.displayMoviePoster(movie.posterPath, SIZE_X, SIZE_Y)
            }
        }

        companion object {
            private const val SIZE_X = 75
            private const val SIZE_Y = 100
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

    override fun onItemDismiss(position: Int) {
        listener.onSwipe((getItem(position) as MovieDomain).id)
    }
}
