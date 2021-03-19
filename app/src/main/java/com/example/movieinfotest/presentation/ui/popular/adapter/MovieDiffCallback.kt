package com.example.movieinfotest.presentation.ui.popular.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movieinfotest.domain.entities.movie.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}