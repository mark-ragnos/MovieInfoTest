package com.example.movieinfotest.listadapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movieinfotest.network.responses.popular.Results

object MovieDiffCallback : DiffUtil.ItemCallback<Results>() {
    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem == newItem
    }
}