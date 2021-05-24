package com.example.movieinfotest.presentation.ui.popular.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movieinfotest.domain.entities.movie.MovieDomain

object MovieDiffCallback : DiffUtil.ItemCallback<MovieDomain>() {
    override fun areItemsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
        return oldItem == newItem
    }
}
