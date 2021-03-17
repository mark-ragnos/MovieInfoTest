package com.example.movieinfotest.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.models.details.MovieDetailsDB
import com.example.movieinfotest.models.popular.Movie
import com.example.movieinfotest.repositories.Repository
import com.example.movieinfotest.ui.popular.adapter.MovieDiffCallback
import com.example.movieinfotest.ui.popular.adapter.MovieHolder
import com.example.movieinfotest.utils.registerImage

class FavoriteAdapter(
    val repository: Repository,
    val listener: MovieDetailClickListener
) : PagingDataAdapter<MovieDetailsDB, FavoriteAdapter.MovieDetailsDbHolder>(MovieDetailsDiffCallback) {

    interface MovieDetailClickListener {
        fun onClick(id: Int)
    }

    object MovieDetailsDiffCallback : DiffUtil.ItemCallback<MovieDetailsDB>() {
        override fun areItemsTheSame(oldItem: MovieDetailsDB, newItem: MovieDetailsDB): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDetailsDB, newItem: MovieDetailsDB): Boolean {
            return oldItem == newItem
        }
    }

    class MovieDetailsDbHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.item_name)
        var rating: TextView = itemView.findViewById(R.id.item_rating)
        var image: ImageView = itemView.findViewById(R.id.item_image)
        var id: TextView = itemView.findViewById(R.id.item_id)
    }

    override fun onBindViewHolder(holder: MovieDetailsDbHolder, position: Int) {
        holder.name.text = getItem(position)?.title
        holder.id.text = getItem(position)?.id.toString()
        holder.rating.text = getItem(position)?.vote_average.toString()
        holder.image.registerImage(getItem(position)?.poster_path)

        holder.itemView.setOnClickListener {
            listener.onClick(holder.id.text.toString().toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsDbHolder {
        return MovieDetailsDbHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }
}